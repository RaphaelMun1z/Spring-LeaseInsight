package com.rm.leaseinsight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.rm.leaseinsight.entities.Staff;
import com.rm.leaseinsight.repositories.StaffRepository;
import com.rm.leaseinsight.services.CacheService;
import com.rm.leaseinsight.services.StaffService;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class StaffServiceTests {
	@InjectMocks
	private StaffService staffService;

	@Mock
	private StaffRepository staffRepository;

	@Mock
	private CacheService cacheService;

	@Test
	@DisplayName("Should create Staff successfully")
	void createStaffSuccessfully() {
		Staff staff = new Staff(null, "Func", "(11) 91111-1111", "func@gmail.com", "teste");
		Mockito.when(staffRepository.save(Mockito.any(Staff.class))).thenReturn(staff);

		Staff createdStaff = staffService.create(staff);

		assertEquals(staff, createdStaff);
		Mockito.verify(staffRepository).save(staff);
		Mockito.verify(cacheService).putStaffCache();
		Mockito.verify(cacheService).putUserCache();
	}

	@Test
	@DisplayName("Should not create Staff")
	void createStaffFail() {
		Staff staff = new Staff(null, "Func", "(11) 91111-1111", "func@gmail.com", null);

		assertThrows(IllegalArgumentException.class, () -> {
			staffService.create(staff);
		});

		Mockito.verify(staffRepository, Mockito.never()).save(Mockito.any(Staff.class));
		Mockito.verify(cacheService, Mockito.never()).putTenantCache();
		Mockito.verify(cacheService, Mockito.never()).putUserCache();
	}
}
