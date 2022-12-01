package com.efuture.rabbitmq.task;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
//@EnableAsync
@Component
public class ChooseCourseTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);
	private AtomicInteger number = new AtomicInteger();


	@Scheduled(fixedDelay = 1)
	//@Async
	public void task1() {
		LOGGER.info(number.incrementAndGet() + " ---------->>> Task 1 Start : "+new Date().toLocaleString());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info(number.get() +" ---------->>> Task 1 End : "+new Date().toLocaleString());
	}
	
	@Scheduled(fixedDelay = 1)
	public void task2() {
		LOGGER.info("==============================>>> Task 2 Start : "+new Date().toLocaleString());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("==============================>>> Task 2 End : "+new Date().toLocaleString());
	}
}
