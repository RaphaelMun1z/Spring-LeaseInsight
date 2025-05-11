package com.rm.leaseinsight.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {
	@Autowired
	private Environment env;

	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory() {
		String host = env.getProperty("spring.redis.host", "localhost");
		int port = Integer.parseInt(env.getProperty("spring.redis.port", "6379"));

		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName(host);
		config.setPort(port);

		return new LettuceConnectionFactory(config);
	}
}
