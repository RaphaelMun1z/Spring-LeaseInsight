package com.rm.leaseinsight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
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
import org.springframework.web.multipart.MultipartFile;

import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.ResidenceAddress;
import com.rm.leaseinsight.entities.enums.OccupancyStatus;
import com.rm.leaseinsight.entities.enums.PropertyType;
import com.rm.leaseinsight.repositories.ResidenceRepository;
import com.rm.leaseinsight.services.AdditionalFeatureService;
import com.rm.leaseinsight.services.CacheService;
import com.rm.leaseinsight.services.OwnerService;
import com.rm.leaseinsight.services.ResidenceAddressService;
import com.rm.leaseinsight.services.ResidenceFeatureService;
import com.rm.leaseinsight.services.ResidenceService;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ResidenceServiceTests {
	@InjectMocks
	private ResidenceService residenceService;

	@Mock
	private ResidenceRepository residenceRepository;

	@Mock
	private ResidenceAddressService residenceAddressService;

	@Mock
	private AdditionalFeatureService additionalFeatureService;

	@Mock
	private ResidenceFeatureService residenceFeatureService;

	@Mock
	private OwnerService ownerService;

	@Mock
	private CacheService cacheService;

	@Test
	@DisplayName("Should create Residence successfully")
	void createResidenceSuccessfully() {
		ResidenceAddress ra = new ResidenceAddress();
		Owner owner = new Owner();
		Residence residence = new Residence(null, owner, PropertyType.HOUSE, "Casa moderna 1", 126, null, "Bloco 10", 4,
				1, 2, 162.13f, 110.49f, 4, Year.of(2023), OccupancyStatus.VACANT, new BigDecimal("2140000"),
				new BigDecimal("2500"), Instant.parse("2024-12-02T10:15:30Z"), ra);

		Mockito.when(residenceRepository.save(Mockito.any(Residence.class))).thenReturn(residence);
		MultipartFile[] images = null;
		
		Residence createdResidence = residenceService.create(residence, images);

		assertEquals(residence, createdResidence);
		Mockito.verify(residenceRepository).save(residence);
		Mockito.verify(cacheService).putResidenceCache();
	}

	@Test
	@DisplayName("Should not create Residence")
	void createResidenceFail() {
		ResidenceAddress ra = new ResidenceAddress(null, null, null, null, null, null, null, null);
		Owner owner = new Owner();
		Residence residence = new Residence(null, owner, PropertyType.HOUSE, "Casa moderna 1", 126, null, "Bloco 10", 4,
				1, 2, 162.13f, 110.49f, 4, Year.of(2023), OccupancyStatus.VACANT, new BigDecimal("2140000"),
				new BigDecimal("2500"), Instant.parse("2024-12-02T10:15:30Z"), ra);
		MultipartFile[] images = null;
		residenceService.create(residence, images);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			residenceService.findById(residence.getId());
		});

		Mockito.verify(residenceRepository, Mockito.times(1)).findById(residence.getId());
		Mockito.verify(cacheService, Mockito.never()).putTenantCache();
		Mockito.verify(cacheService, Mockito.never()).putUserCache();
	}
}
