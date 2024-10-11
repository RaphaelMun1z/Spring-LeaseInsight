package com.rm.leaseinsight;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.rm.leaseinsight.entities.AdditionalFeature;
import com.rm.leaseinsight.repositories.AdditionalFeatureRepository;
import com.rm.leaseinsight.services.AdditionalFeatureService;
import com.rm.leaseinsight.services.CacheService;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AdditionalFeatureServiceTests {
	@InjectMocks
	private AdditionalFeatureService additionalFeatureService;

	@Mock
	private AdditionalFeatureRepository additionalFeatureRepository;

	@Mock
	private CacheService cacheService;

	@Test
	@DisplayName("Should create Additional Feature successfully")
	void createAdditionalFeatureSuccessfully() {
		AdditionalFeature af = new AdditionalFeature(null, "Churrasqueira");
		Mockito.when(additionalFeatureRepository.save(Mockito.any(AdditionalFeature.class))).thenReturn(af);

		AdditionalFeature createdAf = additionalFeatureService.create(af);

		assertEquals(af, createdAf);
		Mockito.verify(additionalFeatureRepository).save(af);
		Mockito.verify(cacheService).putAdditionalFeatureCache();
	}
}
