package com.rm.myadmin.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.AdditionalFeature;

@Service
public class CacheService {
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private AdditionalFeatureService additionalFeatureService;

	public void evictAllCacheValues(String cacheName) {
		Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
	}

	@CachePut("findAllAdditionalFeatures")
	public List<AdditionalFeature> putAdditionalFeatureCache() {
		return additionalFeatureService.findAll();
	}
}
