package com.rm.myadmin.services.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rm.myadmin.services.CacheService;

@Component
public class CacheScheduler {
	@Autowired
	private CacheService cacheService;
}
