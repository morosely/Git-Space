package com.efuture.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class BootSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootSenderApplication.class, args);
	}

}
