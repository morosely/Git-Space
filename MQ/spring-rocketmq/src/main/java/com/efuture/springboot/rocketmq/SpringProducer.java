package com.efuture.springboot.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringProducer {

	@Autowired 
	private RocketMQTemplate rocketMQTemplate;
	
	public void sendMsg(String topic, String msg){

		this.rocketMQTemplate.convertAndSend(topic, msg);
	}
}
