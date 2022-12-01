package com.efuture.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.efuture.rabbitmq.sender.SimpleSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {
	
	@Autowired
    private SimpleSender simpleSender;

	@Test
    public void send() throws Exception {
    	simpleSender.send();
    }
	
}
