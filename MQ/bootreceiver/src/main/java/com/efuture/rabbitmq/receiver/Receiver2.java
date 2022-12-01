package com.efuture.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "work_queue")
public class Receiver2 {
	
	@RabbitHandler
    public void process(String msg) {
        System.out.println("<<<<< ---------- Receiver 2 : " + msg);
    }
}
