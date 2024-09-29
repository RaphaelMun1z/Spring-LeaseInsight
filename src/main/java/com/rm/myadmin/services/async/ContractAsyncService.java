package com.rm.myadmin.services.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.services.ContractService;

@Service
public class ContractAsyncService {
	@Lazy
	@Autowired
	private ContractService contractService;

	@Async("asyncTaskExecutor")
	public CompletableFuture<String> createFirstRental(Contract obj) {
		contractService.createFirstRental(obj);
		return CompletableFuture.completedFuture("Processado!");
	}

	@Async("asyncTaskExecutor")
	public CompletableFuture<String> sendContractBeginEmail(Contract obj) {
		contractService.sendContractBeginEmail(obj);
		return CompletableFuture.completedFuture("Processado!");
	}
}
