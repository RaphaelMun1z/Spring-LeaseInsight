package com.rm.leaseinsight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.rm.leaseinsight.config.FileStorageConfig;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageConfig.class })
@EnableScheduling
@EnableCaching
public class LeaseinsightApplication {
	public static void main(String[] args) {
		SpringApplication.run(LeaseinsightApplication.class, args);
	}
}
