package com.rm.myadmin.config;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.repositories.ContractRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	@Autowired
	private ContractRepository contractRepository;

	@Override
	public void run(String... args) throws Exception {
		Contract c1 = new Contract(null, LocalDate.now(), LocalDate.now(), 1500.0, "Andamento");
		Contract c2 = new Contract(null, LocalDate.now(), LocalDate.now(), 1100.0, "Andamento");

		contractRepository.saveAll(Arrays.asList(c1, c2));
	}

}
