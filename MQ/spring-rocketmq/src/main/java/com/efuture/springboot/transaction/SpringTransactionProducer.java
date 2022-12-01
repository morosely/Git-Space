package com.efuture.springboot.transaction;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SpringTransactionProducer {

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	public void sendMsg(String topic, String msg) { 
		Message message = MessageBuilder.withPayload(msg).build(); 
		// myTransactionGroup要和@RocketMQTransactionListener(txProducerGroup = "springTransactionGroup")定义的一致 
		this.rocketMQTemplate.sendMessageInTransaction("springTransactionGroup", topic, message, null); 
		System.out.println("发送消息成功"); 
	}
}
