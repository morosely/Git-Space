package com.efuture.springboot.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component 
@RocketMQMessageListener(topic = "Spring-Transcation-Topic", consumerGroup = "Spring-Transcation-Consumer", selectorExpression = "*")
public class SpringTranscationConsumer implements RocketMQListener<String>{
	
	@Override 
	public void onMessage(String msg) { 
		System.out.println("接收到消息 -> " + msg); 
	}
}
