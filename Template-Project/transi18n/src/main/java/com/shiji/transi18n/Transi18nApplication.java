package com.shiji.transi18n;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class })
@MapperScan(basePackages = "com.shiji.transi18n.mapper")
@EnableDiscoveryClient
public class Transi18nApplication {

	public static void main(String[] args) {
		SpringApplication.run(Transi18nApplication.class, args);
	}

}
