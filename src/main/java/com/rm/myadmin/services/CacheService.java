package com.rm.myadmin.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.AdditionalFeature;
import com.rm.myadmin.entities.Adm;
import com.rm.myadmin.entities.BillingAddress;
import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.Owner;
import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.ResidenceAddress;
import com.rm.myadmin.entities.ResidenceFeature;
import com.rm.myadmin.entities.Staff;
import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.entities.User;

@Service
public class CacheService {
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	@Lazy
	private AdditionalFeatureService additionalFeatureService;

	@Autowired
	@Lazy
	private AdmService admService;

	@Autowired
	@Lazy
	private BillingAddressService billingAddressService;

	@Autowired
	@Lazy
	private ContractService contractService;

	@Autowired
	@Lazy
	private OwnerService ownerService;

	@Autowired
	@Lazy
	private RentalHistoryService rentalHistoryService;

	@Autowired
	@Lazy
	private ResidenceAddressService residenceAddressService;

	@Autowired
	@Lazy
	private ResidenceFeatureService residenceFeatureService;

	@Autowired
	@Lazy
	private ResidenceService residenceService;

	@Autowired
	@Lazy
	private StaffService staffService;

	@Autowired
	@Lazy
	private TenantService tenantService;

	@Autowired
	@Lazy
	private UserService userService;

	public void evictAllCacheValues(String cacheName) {
		Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
	}

	@CachePut("findAllAdditionalFeatures")
	public List<AdditionalFeature> putAdditionalFeatureCache() {
		return additionalFeatureService.findAll();
	}

	@CachePut("findAllAdm")
	public List<Adm> putAdmCache() {
		return admService.findAll();
	}

	@CachePut("findAllBillingAddress")
	public List<BillingAddress> putBillingAddressCache() {
		return billingAddressService.findAll();
	}

	@CachePut("findAllContract")
	public List<Contract> putContractCache() {
		return contractService.findAll();
	}

	@CachePut("findAllOwner")
	public List<Owner> putOwnerCache() {
		return ownerService.findAll();
	}

	@CachePut("findAllRentalHistory")
	public List<RentalHistory> putRentalHistoryCache() {
		return rentalHistoryService.findAll();
	}

	@CachePut("findAllResidenceAddress")
	public List<ResidenceAddress> putResidenceAddressCache() {
		return residenceAddressService.findAll();
	}

	@CachePut("findAllResidenceFeature")
	public List<ResidenceFeature> putResidenceFeatureCache() {
		return residenceFeatureService.findAll();
	}

	@CachePut("findAllResidence")
	public List<Residence> putResidenceCache() {
		return residenceService.findAll();
	}

	@CachePut("findAllStaff")
	public List<Staff> putStaffCache() {
		return staffService.findAll();
	}

	@CachePut("findAllTenant")
	public List<Tenant> putTenantCache() {
		return tenantService.findAll();
	}

	@CachePut("findAllUser")
	public List<User> putUserCache() {
		return userService.findAll();
	}
}
