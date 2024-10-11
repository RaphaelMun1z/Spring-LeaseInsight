package com.rm.leaseinsight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.ResidenceAddress;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.ContractStatus;
import com.rm.leaseinsight.entities.enums.OccupancyStatus;
import com.rm.leaseinsight.entities.enums.PropertyType;
import com.rm.leaseinsight.repositories.ContractRepository;
import com.rm.leaseinsight.repositories.ResidenceRepository;
import com.rm.leaseinsight.services.CacheService;
import com.rm.leaseinsight.services.ContractService;
import com.rm.leaseinsight.services.RentalHistoryService;
import com.rm.leaseinsight.services.ResidenceService;
import com.rm.leaseinsight.services.TenantService;
import com.rm.leaseinsight.services.async.ContractAsyncService;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ContractServiceTests {
	@InjectMocks
	private ContractService contractService;

	@Mock
	private ContractRepository contractRepository;

	@Mock
	private ResidenceService residenceService;

	@Mock
	private ResidenceRepository residenceRepository;

	@Mock
	private TenantService tenantService;

	@Mock
	private RentalHistoryService rentalHistoryService;

	@Mock
	private ContractAsyncService contractAsyncService;

	@Mock
	private CacheService cacheService;

	@Test
	@DisplayName("Should create Contract successfully")
	void createContractSuccessfully() {
		Residence residence = new Residence("residence123", new Owner(), PropertyType.HOUSE, "Casa moderna 1", 126,
				null, "Bloco 10", 4, 1, 2, 162.13f, 110.49f, 4, Year.of(2021), OccupancyStatus.VACANT,
				new BigDecimal("2140000"), new BigDecimal("2500"), Instant.now(), new ResidenceAddress());
		Tenant tenant = new Tenant();
		Contract contract = new Contract(null, residence, tenant, LocalDate.now(), LocalDate.now().plusYears(1), 1750.0,
				ContractStatus.ACTIVE, 15);

		Mockito.when(residenceService.findById(residence.getId())).thenReturn(residence);
		Mockito.when(tenantService.findById(tenant.getId())).thenReturn(tenant);
		Mockito.when(contractRepository.save(Mockito.any(Contract.class))).thenReturn(contract);

		Contract createdContract = contractService.create(contract);

		assertEquals(contract, createdContract);
		Mockito.verify(contractRepository).save(contract);
		Mockito.verify(cacheService).putContractCache();
		Mockito.verify(contractAsyncService).sendContractBeginEmail(contract);
	}

	@Test
	@DisplayName("Should not create Contract")
	void createContractFail() {
		Residence residence = new Residence("residence123", new Owner(), PropertyType.HOUSE, "Casa moderna 1", 126,
				null, "Bloco 10", 4, 1, 2, 162.13f, 110.49f, 4, Year.of(2021), OccupancyStatus.VACANT,
				new BigDecimal("2140000"), new BigDecimal("2500"), Instant.now(), new ResidenceAddress());
		Tenant tenant = null;
		Contract contract = new Contract("abc", residence, tenant, LocalDate.now(), LocalDate.now().plusYears(1),
				1750.0, ContractStatus.ACTIVE, 15);

		assertThrows(NullPointerException.class, () -> {
			contractService.create(contract);
		});

		Mockito.verify(contractRepository, Mockito.never()).save(Mockito.any(Contract.class));
		Mockito.verify(cacheService, Mockito.never()).putContractCache();
		Mockito.verify(contractAsyncService, Mockito.never()).sendContractBeginEmail(contract);
	}
}
