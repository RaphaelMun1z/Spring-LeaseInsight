package com.rm.leaseinsight.services.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rm.leaseinsight.entities.Staff;
import com.rm.leaseinsight.entities.enums.TemplatesEnum;
import com.rm.leaseinsight.services.EmailService;

@Service
public class AccountAsyncService {
	@Lazy
	@Autowired
	private EmailService emailService;

	@Async("asyncTaskExecutor")
	public CompletableFuture<String> sendNewAccountEmail(Staff s, String password) {
		emailService.sendPasswordEmail(TemplatesEnum.NEW_ACCOUNT, s.getName(), s.getEmail(), "Bem-vindo(a) à LeaseInsight", password);
		return CompletableFuture.completedFuture("Processado!");
	}
}
