package com.rm.leaseinsight.services.scheduling;

import java.time.LocalDate;
import java.time.Year;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rm.leaseinsight.dto.RentalHistoryRequestDTO;
import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.enums.ContractStatus;
import com.rm.leaseinsight.entities.enums.PaymentStatus;
import com.rm.leaseinsight.services.ContractService;
import com.rm.leaseinsight.services.RentalHistoryService;
import com.rm.leaseinsight.services.async.ContractAsyncService;

@Component
public class ContractScheduler {
	@Autowired
	private ContractService contractService;

	@Autowired
	private ContractAsyncService contractAsyncService;

	@Autowired
	private RentalHistoryService rentalHistoryService;

	@Scheduled(cron = "0 33 19 * * *")
	public void checkContracts() {
		LocalDate today = LocalDate.now();
		Set<Contract> contracts = contractService.findByContractStatus(1);

		for (Contract c : contracts) {
			try {
				if (c.getContractEndDate().equals(today)) {
					c.setContractStatus(ContractStatus.TERMINATED);
					contractService.patch(c.getId(), c);
				} else {
					int dueDate = c.getInvoiceDueDate();
					if ((int) dueDate > today.getMonth().length(Year.isLeap(today.getYear()))) {
						dueDate = today.getMonth().length(Year.isLeap(today.getYear()));
					}

					if (dueDate == today.getDayOfMonth()) {
						System.out.println("O contrato com ID " + c.getId() + " está vencido hoje.");
						RentalHistory rental = new RentalHistory(null, today, PaymentStatus.PENDING, c);
						rentalHistoryService.create(new RentalHistoryRequestDTO(rental));
						contractAsyncService.sendInvoiceByEmail(c, rental);
					}
				}
			} catch (Exception e) {
				System.err.println("Erro ao processar contrato com ID " + c.getId() + ": " + e.getMessage());
				e.printStackTrace();
			}
		}
		System.out.println("verificações do dia finalizadas.");
	}
}
