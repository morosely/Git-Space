package com.efuture.springboot.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component 
@RocketMQMessageListener(topic = "Spring-Topic", consumerGroup = "Spring-Consumer-Group", selectorExpression = "*")
public class SpringConsumer implements RocketMQListener<String> {

	@Override
	public void onMessage(String message) {

		System.out.println("接收到消息 -> " + message);
	}

}
