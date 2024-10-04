package com.rm.myadmin;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.rm.myadmin.entities.BillingAddress;
import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.entities.User;
import com.rm.myadmin.entities.enums.TenantStatus;
import com.rm.myadmin.repositories.UserRepository;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
class TenantRepositoryTests {
	@Autowired
	UserRepository<User> userRepository;

	@Autowired
	EntityManager entityManager;

	@Test
	@DisplayName("Should get Tenant successfully from DB")
	void findByEmailCase1() {
		BillingAddress ba = new BillingAddress(null, 502, "Av. Pres. Kennedy", "Guilhermina", "Praia Grande",
				"São Paulo", "Brasil", "33444-111", "Próximo ao Extra");
		BillingAddress bas = createBillingAddress(ba);

		Tenant data = new Tenant(null, "Nautilus", "(13) 91212-1212", "raphaelmunizvarela@gmail.com",
				"$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu", LocalDate.of(2000, 6, 15),
				"507.205.280-03", "22.222.222-2", LocalDate.now(), TenantStatus.PENDING, bas);
		createTenant(data);

		UserDetails result = userRepository.findByEmail(data.getEmail());
		assertThat(result);
	}

	@Test
	@DisplayName("Should not get Tenant from DB when tenant not exists")
	void findByEmailCase2() {
		BillingAddress ba = new BillingAddress(null, 502, "Av. Pres. Kennedy", "Guilhermina", "Praia Grande",
				"São Paulo", "Brasil", "33444-111", "Próximo ao Extra");
		BillingAddress bas = createBillingAddress(ba);

		Tenant data = new Tenant(null, "Nautilus", "(13) 91212-1212", "raphaelmunizvarela@gmail.com",
				"$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu", LocalDate.of(2000, 6, 15),
				"507.205.280-03", "22.222.222-2", LocalDate.now(), TenantStatus.PENDING, bas);

		UserDetails result = userRepository.findByEmail(data.getEmail());
		assertThat(result);
	}

	private Tenant createTenant(Tenant data) {
		Tenant newTenant = new Tenant(null, data.getName(), data.getPhone(), data.getEmail(), data.getPassword(),
				data.getDateOfBirth(), data.getCpf(), data.getRg(), data.getRegistrationDate(), data.getTenantStatus(),
				data.getTenantBillingAddress());
		this.entityManager.persist(newTenant);
		return newTenant;
	}

	private BillingAddress createBillingAddress(BillingAddress ba) {
		BillingAddress newBa = new BillingAddress(ba.getId(), ba.getNumber(), ba.getStreet(), ba.getDistrict(),
				ba.getCity(), ba.getState(), ba.getCountry(), ba.getCep(), ba.getComplement());
		this.entityManager.persist(newBa);
		return newBa;
	}

}
