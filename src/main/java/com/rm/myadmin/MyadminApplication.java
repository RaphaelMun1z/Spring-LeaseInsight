package com.rm.myadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.rm.myadmin.config.FileStorageConfig;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageConfig.class })
@EnableScheduling
@EnableCaching
public class MyadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyadminApplication.class, args);
	}
}
