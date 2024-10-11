package com.rm.leaseinsight.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rm.leaseinsight.services.CacheService;

@RestController
@RequestMapping("/cache")
public class CacheResource {
	@Autowired
	private CacheService service;

	@DeleteMapping("/clear")
	public ResponseEntity<Void> clear(@RequestParam("cacheName") String cacheName) {
		try {
			service.evictAllCacheValues(cacheName);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(404).build();
		}
	}

	@PutMapping(value = "/additional-features")
	public ResponseEntity<Void> updateAdditionalFeatureCache() {
		try {
			service.putAdditionalFeatureCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/adm")
	public ResponseEntity<Void> updateAdmCache() {
		try {
			service.putAdmCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/billing-addresses")
	public ResponseEntity<Void> updateBillingAddressCache() {
		try {
			service.putBillingAddressCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/contracts")
	public ResponseEntity<Void> updateContractCache() {
		try {
			service.putContractCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/owners")
	public ResponseEntity<Void> updateOwnerCache() {
		try {
			service.putOwnerCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/rental-histories")
	public ResponseEntity<Void> updateRentalHistoryCache() {
		try {
			service.putRentalHistoryCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/residence-addresses")
	public ResponseEntity<Void> updateResidenceAddressCache() {
		try {
			service.putResidenceAddressCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/residences")

	public ResponseEntity<Void> updateResidenceCache() {
		try {
			service.putResidenceCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/staffs")
	public ResponseEntity<Void> updateStaffCache() {
		try {
			service.putStaffCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/tenants")
	public ResponseEntity<Void> updateTenantCache() {
		try {
			service.putTenantCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping(value = "/users")
	public ResponseEntity<Void> updateUserCache() {
		try {
			service.putUserCache();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
}
