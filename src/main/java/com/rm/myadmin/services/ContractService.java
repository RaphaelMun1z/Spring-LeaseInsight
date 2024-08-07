package com.rm.myadmin.services;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.entities.enums.ContractStatus;
import com.rm.myadmin.entities.enums.PaymentStatus;
import com.rm.myadmin.repositories.ContractRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContractService {
	@Autowired
	private ContractRepository repository;

	@Autowired
	private ResidenceService residenceService;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private RentalHistoryService rentalHistoryService;

	@Autowired
	private EmailService emailService;

	public List<Contract> findAll() {
		return repository.findAll();
	}

	public Contract findById(Long id) {
		Optional<Contract> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Contract create(Contract obj) {
		Residence r = residenceService.findById(obj.getResidence().getId());
		obj.setResidence(r);
		r.setContract(obj);
		obj.setTenant(tenantService.findById(obj.getTenant().getId()));
		Contract contract = repository.save(obj);
		createFirstRental(obj);
		return contract;
	}

	private void createFirstRental(Contract c) {
		RentalHistory rental = new RentalHistory(null, c.getContractStartDate(), PaymentStatus.PENDING, c);
		rentalHistoryService.create(rental);
	}

	public void delete(Long id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Contract update(Long id, Contract obj) {
		try {
			Contract entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Contract entity, Contract obj) {
		entity.setContractStartDate(obj.getContractStartDate());
		entity.setContractEndDate(obj.getContractEndDate());
		entity.setDefaultRentalValue(obj.getDefaultRentalValue());
		entity.setContractStatus(obj.getContractStatus());
		entity.setInvoiceDueDate(obj.getInvoiceDueDate());
	}

	public Set<Contract> findByContractStatus(Integer code) {
		return repository.findByContractStatus(code);
	}

	public Set<Contract> findByTenant(Long id) {
		Tenant tenant = tenantService.findById(id);
		return repository.findByTenant(tenant);
	}

	@Scheduled(cron = "0 50 20 * * *")
	public void checkContractInvoiceDueDate() {
		LocalDate today = LocalDate.now();
		Set<Contract> contracts = this.findByContractStatus(1);

		for (Contract c : contracts) {
			if (c.getContractEndDate().equals(today)) {
				c.setContractStatus(ContractStatus.TERMINATED);
			} else {
				int dueDate = c.getInvoiceDueDate();
				if ((int) dueDate > today.getMonth().length(Year.isLeap(today.getYear()))) {
					dueDate = today.getMonth().length(Year.isLeap(today.getYear()));
				}

				if (dueDate == today.getDayOfMonth()) {
					System.out.println("O contrato com ID " + c.getId() + " está vencido hoje.");
					RentalHistory rental = new RentalHistory(null, today, PaymentStatus.PENDING, c);
					rentalHistoryService.create(rental);
					String emailBody = "Olá " + c.getTenant().getName() + ",\n\n" + "Esperamos que você esteja bem!\n\n"
							+ "Gostaríamos de informar que a sua fatura de aluguel referente ao período de "
							+ rental.getRentalStartDate() + " - " + rental.getRentalEndDate()
							+ " já está disponível para consulta.\n\n" + "Detalhes da Fatura:\n" + "- Valor: R$ "
							+ rental.getRentalValue() + "\n" + "- Data de Vencimento: " + rental.getRentalEndDate()
							+ "\n\n"
							+ "Para acessar a fatura e realizar o pagamento, por favor, acesse o nosso portal através do link abaixo:\n"
							+ "www.myadmin.com\n\n"
							+ "Se tiver qualquer dúvida ou precisar de assistência, não hesite em entrar em contato conosco.\n\n"
							+ "Agradecemos pela sua atenção.\n\n" + "Atenciosamente,\n" + "MyAdmin Locações\n" + "---\n"
							+ "Este é um e-mail automático, por favor, não responda.";

					System.out.println(emailService.sendEmail(c.getTenant().getEmail(),
							"[Fatura Disponível] Sua Fatura de Aluguel Já Está Disponível", emailBody));
				}
			}
		}
		System.out.println("verificações do dia finalizadas.");
	}
}
