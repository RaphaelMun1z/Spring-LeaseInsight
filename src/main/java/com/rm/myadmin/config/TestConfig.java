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

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.enums.ContractStatus;
import com.rm.myadmin.entities.enums.OccupancyStatus;
import com.rm.myadmin.entities.enums.PaymentStatus;
import com.rm.myadmin.entities.enums.PropertyType;
import com.rm.myadmin.repositories.ContractRepository;
import com.rm.myadmin.repositories.RentalHistoryRepository;
import com.rm.myadmin.repositories.ResidenceRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private RentalHistoryRepository rentalHistoryRepository;

	@Autowired
	private ResidenceRepository residenceRepository;

	@Override
	public void run(String... args) throws Exception {
		Residence r1 = new Residence(null, PropertyType.House, "Casa nova, moderna, alto padrão.", null,
				"Condomínio ABC", 3, 2, 1, 112.5f, 87.3f, 2, Year.of(2023), OccupancyStatus.Occupied,
				new BigDecimal("1615900.00"), new BigDecimal("3500.00"), Instant.parse("2024-07-10T12:35:12Z"));
		Residence r2 = new Residence(null, PropertyType.Apartment, "Apartamento com vista para o mar, bem iluminado.",
				101, "Edifício Solar", 2, 1, 0, 75.0f, 65.0f, 1, Year.of(2022), OccupancyStatus.PendingMoveOut,
				new BigDecimal("850000.00"), new BigDecimal("2000.00"), Instant.parse("2024-06-15T08:30:00Z"));

		//residenceRepository.saveAll(Arrays.asList(r1, r2));

		Contract c1 = new Contract(null, r1, LocalDate.now(), LocalDate.now(), 1500.0, ContractStatus.Active);
		Contract c2 = new Contract(null, r2, LocalDate.now(), LocalDate.now(), 1100.0, ContractStatus.Renewed);

		contractRepository.saveAll(Arrays.asList(c1, c2));

		r1.setContract(c1);
		r2.setContract(c2);

		residenceRepository.saveAll(Arrays.asList(r1, r2));

		RentalHistory rh1 = new RentalHistory(null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31),
				PaymentStatus.Paid, c1);
		RentalHistory rh2 = new RentalHistory(null, LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 29),
				PaymentStatus.Overdue, c1);
		RentalHistory rh3 = new RentalHistory(null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31),
				PaymentStatus.Pending, c2);

		rentalHistoryRepository.saveAll(Arrays.asList(rh1, rh2, rh3));
	}

}
