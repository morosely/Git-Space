package com.efuture.springboot.test;

import com.efuture.springboot.BootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.efuture.springboot.rocketmq.SpringProducer;
import com.efuture.springboot.transaction.SpringTransactionProducer;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = BootApplication.class)
public class TestSpringRocketMQ {

	@Autowired
	private SpringProducer springProducer;
	
	@Autowired 
	private SpringTransactionProducer springTransactionProducer;

	@Test
	public void sendSpringMsg() {
		this.springProducer.sendMsg("Spring-Topic", "第一个Spring消息");
	}
	
	@Test
	public void sendSpringTransactionMsg() {
		this.springTransactionProducer.sendMsg("Spring-Transcation-Topic", "第一个Spring Transcation消息");
	}
}
