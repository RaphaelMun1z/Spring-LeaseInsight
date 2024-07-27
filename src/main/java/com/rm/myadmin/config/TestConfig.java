package com.rm.myadmin.config;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.repositories.ContractRepository;
import com.rm.myadmin.repositories.RentalHistoryRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private RentalHistoryRepository rentalHistoryRepository;

	@Override
	public void run(String... args) throws Exception {
		Contract c1 = new Contract(null, LocalDate.now(), LocalDate.now(), 1500.0, "Andamento");
		Contract c2 = new Contract(null, LocalDate.now(), LocalDate.now(), 1100.0, "Andamento");

		RentalHistory rh1 = new RentalHistory(null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31), "Paga", c1);
		RentalHistory rh2 = new RentalHistory(null, LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 29), "Atrasada",
				c1);
		RentalHistory rh3 = new RentalHistory(null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31), "Pendente",
				c2);

		contractRepository.saveAll(Arrays.asList(c1, c2));
		rentalHistoryRepository.saveAll(Arrays.asList(rh1, rh2, rh3));
	}

}
