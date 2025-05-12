package com.rm.leaseinsight.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rm.leaseinsight.entities.Adm;
import com.rm.leaseinsight.repositories.AdmRepository;

@Configuration
@Profile("prod")
public class TestConfig implements CommandLineRunner {
	@Autowired
	private AdmRepository admRepository;

	@Override
	public void run(String... args) throws Exception {
		String email = "irineu@gmail.com";
		if (admRepository.findByEmail(email) == null) {
			Adm adm = new Adm(null, "Irineu", "(11) 91234-5678", email,
					"$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu");
			admRepository.save(adm);
		}
	}

}
