package com.rm.myadmin.services.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.entities.enums.TemplatesEnum;
import com.rm.myadmin.services.EmailService;

@Service
public class ContractAsyncService {
	@Lazy
	@Autowired
	private EmailService emailService;

	@Async("asyncTaskExecutor")
	public CompletableFuture<String> sendContractBeginEmail(Contract c) {
		emailService.sendEmail(TemplatesEnum.WELCOME, c.getTenant().getName(), c.getTenant().getEmail(),
				"Bem-vindo(a) à LeaseInsight");
		return CompletableFuture.completedFuture("Processado!");
	}

	@Async("asyncTaskExecutor")
	public CompletableFuture<String> sendInvoiceByEmail(Contract c, RentalHistory rental) {
		emailService.sendEmail(TemplatesEnum.INVOICE, c.getTenant().getName(), c.getTenant().getEmail(),
				"[Fatura Disponível] Sua Fatura de Aluguel Já Está Disponível");
		return CompletableFuture.completedFuture("Processado!");
	}
}
