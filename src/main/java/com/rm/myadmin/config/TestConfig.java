package com.rm.myadmin.config;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rm.myadmin.entities.AdditionalFeature;
import com.rm.myadmin.entities.BillingAddress;
import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.Owner;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.ResidenceAddress;
import com.rm.myadmin.entities.ResidenceFeature;
import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.entities.enums.ContractStatus;
import com.rm.myadmin.entities.enums.OccupancyStatus;
import com.rm.myadmin.entities.enums.PropertyType;
import com.rm.myadmin.entities.enums.TenantStatus;
import com.rm.myadmin.repositories.AdditionalFeatureRepository;
import com.rm.myadmin.repositories.BillingAddressRepository;
import com.rm.myadmin.repositories.ContractRepository;
import com.rm.myadmin.repositories.OwnerRepository;
import com.rm.myadmin.repositories.ResidenceAddressRepository;
import com.rm.myadmin.repositories.ResidenceFeatureRepository;
import com.rm.myadmin.repositories.ResidenceRepository;
import com.rm.myadmin.repositories.TenantRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private ResidenceRepository residenceRepository;

	@Autowired
	private ResidenceAddressRepository residenceAddressRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private AdditionalFeatureRepository additionalFeatureRepository;

	@Autowired
	private ResidenceFeatureRepository residenceFeatureRepository;

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private BillingAddressRepository billingAddressRepository;

	@Override
	public void run(String... args) throws Exception {

		Owner o1 = new Owner(null, "Garen Demácia", "(13) 91234-5678", "garendemacia@gmail.com", "password123");
		Owner o2 = new Owner(null, "Lux", "(11) 94321-8765", "lux@gmail.com", "senha321");
		ownerRepository.saveAll(Arrays.asList(o1, o2));

		BillingAddress ba1 = new BillingAddress(null, 502, "Av. Pres. Kennedy", "Guilhermina", "Praia Grande",
				"São Paulo", "Brasil", "33444-111", "Próximo ao Extra");
		billingAddressRepository.save(ba1);

		Tenant t1 = new Tenant(null, "Nautilus", "(13) 91212-1212", "raphaelmunizvarela@gmail.com", "senhaa123",
				LocalDate.of(2000, 6, 15), "507.205.280-03", "22.222.222-2", LocalDate.now(), TenantStatus.PENDING,
				ba1);
		tenantRepository.save(t1);

		ResidenceAddress ra1 = new ResidenceAddress(null, 315, "Av. Mal. Mallet", "Canto do Forte", "Praia Grande",
				"São Paulo", "Brasil", "11222-333", "Próximo ao quartel");
		residenceAddressRepository.save(ra1);

		Residence r1 = new Residence(null, o1, PropertyType.HOUSE, "Casa nova, moderna, alto padrão.", null,
				"Condomínio ABC", 3, 2, 1, 112.5f, 87.3f, 2, Year.of(2023), OccupancyStatus.OCCUPIED,
				new BigDecimal("1615900.00"), new BigDecimal("3500.00"), Instant.parse("2024-07-10T12:35:12Z"), ra1);
		Residence r2 = new Residence(null, o1, PropertyType.APARTMENT,
				"Apartamento com vista para o mar, bem iluminado.", 101, "Edifício Solar", 2, 1, 0, 75.0f, 65.0f, 1,
				Year.of(2022), OccupancyStatus.PENDING_MOVE_OUT, new BigDecimal("850000.00"), new BigDecimal("2000.00"),
				Instant.parse("2024-06-15T08:30:00Z"), ra1);
		residenceRepository.saveAll(Arrays.asList(r1, r2));

		Contract c1 = new Contract(null, r1, t1, LocalDate.of(2024, 4, 20), LocalDate.of(2024, 10, 10), 950.00,
				ContractStatus.ACTIVE, 5);
		Contract c2 = new Contract(null, r2, t1, LocalDate.of(2024, 4, 20), LocalDate.of(2024, 10, 20), 1100.0,
				ContractStatus.RENEWED, 6);
		contractRepository.saveAll(Arrays.asList(c1, c2));

		r1.setContract(c1);
		r2.setContract(c2);

//		RentalHistory rh1 = new RentalHistory(null, LocalDate.of(2024, 4, 20), PaymentStatus.PAID, c1);
//		RentalHistory rh2 = new RentalHistory(null, LocalDate.of(2024, 5, 10), PaymentStatus.OVERDUE, c1);
//		RentalHistory rh3 = new RentalHistory(null, LocalDate.of(2024, 6, 10), PaymentStatus.PENDING, c1);
//		rentalHistoryRepository.saveAll(Arrays.asList(rh1, rh2, rh3));

		AdditionalFeature af1 = new AdditionalFeature(null, "Piscina");
		AdditionalFeature af2 = new AdditionalFeature(null, "Churrasqueira");
		AdditionalFeature af3 = new AdditionalFeature(null, "Placa solar");
		AdditionalFeature af4 = new AdditionalFeature(null, "Academia");
		additionalFeatureRepository.saveAll(Arrays.asList(af1, af2, af3, af4));

		// ResidenceFeature rf1 = new ResidenceFeature(r1, af1);
		ResidenceFeature rf2 = new ResidenceFeature(r1, af2);
		ResidenceFeature rf3 = new ResidenceFeature(r1, af3);
		ResidenceFeature rf4 = new ResidenceFeature(r2, af1);
		ResidenceFeature rf5 = new ResidenceFeature(r2, af4);
		residenceFeatureRepository.saveAll(Arrays.asList(rf2, rf3, rf4, rf5));
	}

}
