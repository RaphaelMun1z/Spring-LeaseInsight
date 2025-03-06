package com.rm.leaseinsight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.rm.leaseinsight.dto.req.TenantRequestDTO;
import com.rm.leaseinsight.dto.res.TenantResponseDTO;
import com.rm.leaseinsight.entities.BillingAddress;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.TenantStatus;
import com.rm.leaseinsight.repositories.TenantRepository;
import com.rm.leaseinsight.services.BillingAddressService;
import com.rm.leaseinsight.services.CacheService;
import com.rm.leaseinsight.services.TenantService;
import com.rm.leaseinsight.services.async.TenantAsyncService;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TenantServiceTests {
	@InjectMocks
	private TenantService tenantService;

	@Mock
	private TenantAsyncService tenantAsyncService;

	@Mock
	private TenantRepository tenantRepository;

	@Mock
	private BillingAddressService billingAddressService;

	@Mock
	private CacheService cacheService;

	@Test
	@DisplayName("Should create Tenant successfully")
	void createTenantSuccessfully() {
		BillingAddress ba = new BillingAddress("def", 502, "Av. Pres. Kennedy", "Guilhermina", "Praia Grande",
				"São Paulo", "Brasil", "33444-111", "Próximo ao Extra");
		Mockito.when(billingAddressService.findById("def")).thenReturn(ba);

		Tenant tenant = new Tenant(null, "Nautilus", "(13) 91212-1212", "raphaelmunizvarela@gmail.com",
				"$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu", LocalDate.of(2000, 6, 15),
				"507.205.280-03", "22.222.222-2", LocalDate.now(), TenantStatus.PENDING, ba);
		Mockito.when(tenantRepository.save(Mockito.any(Tenant.class))).thenReturn(tenant);

		TenantRequestDTO tenantRequest = new TenantRequestDTO(tenant);
		TenantResponseDTO createdTenant = tenantService.create(tenantRequest);

		assertEquals(tenant, createdTenant);
		Mockito.verify(billingAddressService).findById("def");
		Mockito.verify(tenantRepository).save(tenant);
		Mockito.verify(cacheService).putTenantCache();
		Mockito.verify(cacheService).putUserCache();
	}

	@Test
	@DisplayName("Should not create Tenant, invalid Billing Address ID")
	void createTenantFail() {
		Mockito.when(billingAddressService.findById("def")).thenThrow(new ResourceNotFoundException("def"));

		Tenant tenant = new Tenant("abc", "Nautilus", "(13) 91212-1212", "raphaelmunizvarela@gmail.com",
				"$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu", LocalDate.of(2000, 6, 15),
				"507.205.280-03", "22.222.222-2", LocalDate.now(), TenantStatus.PENDING,
				new BillingAddress("def", 0, null, null, null, null, null, null, null));

		assertThrows(ResourceNotFoundException.class, () -> {
			TenantRequestDTO tenantRequest = new TenantRequestDTO(tenant);
			tenantService.create(tenantRequest);
		});

		Mockito.verify(billingAddressService).findById("def");
		Mockito.verify(tenantRepository, Mockito.never()).save(Mockito.any(Tenant.class));
		Mockito.verify(cacheService, Mockito.never()).putTenantCache();
		Mockito.verify(cacheService, Mockito.never()).putUserCache();
	}
}
