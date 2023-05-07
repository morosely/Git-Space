package com.shiji.transi18n;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication
public class Transi18nApplication {

	public static void main(String[] args) {
		SpringApplication.run(Transi18nApplication.class, args);
	}

}
