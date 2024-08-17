package com.rm.myadmin.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rm.myadmin.services.CacheService;

@RestController
@RequestMapping("/cache")
public class CacheResource {
	@Autowired
	private CacheService service;

	@PostMapping("/clear")
	public void clear(@RequestParam("cacheName") String cacheName) {
		service.evictAllCacheValues(cacheName);
	}

	@PutMapping(value = "/additional-features")
	public void updateAdditionalFeatureCache() {
		service.putAdditionalFeatureCache();
	}
}
