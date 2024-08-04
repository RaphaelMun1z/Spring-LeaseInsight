package com.rm.myadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyadminApplication.class, args);
	}
}
