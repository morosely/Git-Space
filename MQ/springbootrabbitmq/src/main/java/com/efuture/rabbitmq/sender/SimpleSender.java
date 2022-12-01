package com.efuture.rabbitmq.sender;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleSender {

	@Autowired 
	private AmqpTemplate amqpTemplate;
	
	@Autowired
    private Queue queue;
    
    public void send() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//24小时制
        String context = "Hello,World! " + date;
        System.out.println("---------- >>>>> Sender : " + context);
        this.amqpTemplate.convertAndSend(queue.getName(), context);
    }
}
