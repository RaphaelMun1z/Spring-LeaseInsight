package com.rm.leaseinsight.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
public class RedisDebugController {
	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@GetMapping("/ping")
	public String pingRedis() {
		try {
			String pong = redisConnectionFactory.getConnection().ping();
			return "Redis respondeu: " + pong + " (host=" + redisHost + ", port=" + redisPort + ")";
		} catch (Exception e) {
			return "Erro ao conectar ao Redis (host=" + redisHost + ", port=" + redisPort + "): " + e.getMessage();
		}
	}
}
