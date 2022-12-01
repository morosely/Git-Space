package com.efuture.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "simple_queue")
public class SimpleReceiver {

	@RabbitHandler
    public void receive(String message) {
        System.out.println("<<<<< ----------  Received : " + message );
    }
}
