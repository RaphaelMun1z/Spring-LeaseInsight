package com.rm.myadmin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rm.myadmin.entities.Adm;
import com.rm.myadmin.repositories.AdmRepository;

@Configuration
@Profile("dev")
public class TestConfig implements CommandLineRunner {
	@Autowired
	private AdmRepository admRepository;

	@Override
	public void run(String... args) throws Exception {
		Adm adm = new Adm(null, "Irineu", "(11) 91234-5678", "irineu@gmail.com",
				"$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu");
		admRepository.save(adm);
	}

}
