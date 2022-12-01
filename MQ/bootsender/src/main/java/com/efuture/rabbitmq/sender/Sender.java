package com.efuture.rabbitmq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Autowired
	private Queue queue;

	public void send(String msg) {
		System.out.println(queue.getName() + "---------->>>>> Sender  : " + msg);
		this.rabbitTemplate.convertAndSend(queue.getName(), msg);
	}
}
