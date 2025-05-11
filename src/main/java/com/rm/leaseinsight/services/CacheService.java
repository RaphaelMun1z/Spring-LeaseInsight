package com.rm.leaseinsight.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rm.leaseinsight.dto.res.ContractResponseDTO;
import com.rm.leaseinsight.dto.res.RentalHistoryResponseDTO;
import com.rm.leaseinsight.entities.AdditionalFeature;
import com.rm.leaseinsight.entities.Adm;
import com.rm.leaseinsight.entities.BillingAddress;
import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Report;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.ResidenceAddress;
import com.rm.leaseinsight.entities.Staff;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.User;

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

	@Autowired
	@Lazy
	private ReportService reportService;

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
	public List<ContractResponseDTO> putContractCache() {
		return contractService.findAll();
	}

	@CachePut("findAllOwner")
	public List<Owner> putOwnerCache() {
		return ownerService.findAll();
	}

	@CachePut("findAllRentalHistory")
	public List<RentalHistoryResponseDTO> putRentalHistoryCache() {
		return rentalHistoryService.findAll();
	}

	@CachePut("findAllResidenceAddress")
	public List<ResidenceAddress> putResidenceAddressCache() {
		return residenceAddressService.findAll();
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
		List<User> users = new ArrayList<>();
		users.addAll(putAdmCache());
		users.addAll(putStaffCache());
		users.addAll(putTenantCache());
		return users;
	}

	@CachePut("findAllReport")
	public List<Report> putReportCache() {
		return reportService.findAll();
	}
}
