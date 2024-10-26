package com.rm.leaseinsight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.rm.leaseinsight.config.FileStorageConfig;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageConfig.class })
@EnableScheduling
@EnableCaching
public class LeaseinsightApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("src/main/resources/").filename(".env").load();

		System.setProperty("DB_URL_PROD", dotenv.get("DB_URL_PROD"));
		System.setProperty("DB_URL_DEV", dotenv.get("DB_URL_DEV"));
	    System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
	    System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
	    System.setProperty("REDIS_HOST", dotenv.get("REDIS_HOST"));
	    System.setProperty("REDIS_PORT", dotenv.get("REDIS_PORT"));
	    System.setProperty("REDIS_TTL", dotenv.get("REDIS_TTL"));
	    System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
	    System.setProperty("MAIL_PORT", dotenv.get("MAIL_PORT"));
	    System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
	    System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
	    System.setProperty("UPLOAD_DIR", dotenv.get("UPLOAD_DIR"));
	    System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));

		SpringApplication.run(LeaseinsightApplication.class, args);
	}
}
