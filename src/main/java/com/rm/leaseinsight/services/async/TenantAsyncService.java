package com.rm.leaseinsight.services.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.TemplatesEnum;
import com.rm.leaseinsight.services.EmailService;

@Service
public class TenantAsyncService {
	@Lazy
	@Autowired
	private EmailService emailService;

	public CompletableFuture<String> sendNewTenantEmail(Tenant t) {
		emailService.sendEmail(TemplatesEnum.WELCOME, t.getName(), t.getEmail(), "Bem-vindo(a) à LeaseInsight");
		return CompletableFuture.completedFuture("Processado!");
	}
}
