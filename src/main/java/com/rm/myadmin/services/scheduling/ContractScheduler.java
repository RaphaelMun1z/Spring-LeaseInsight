package com.rm.myadmin.services.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ContractScheduler {
	@Scheduled(cron = "0 0 0 * * ?")
	public void checkExpiringContracts() {
        System.out.println("Verificando contratos prestes a expirar...");
    }
	
	@Scheduled(cron = "0 44 22 * * *")
	public void teste() {
		System.out.println("testeeeee...");
	}
}
