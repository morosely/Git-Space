package com.efuture.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.efuture.rabbitmq.sender.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootSenderApplicationTests {

	@Autowired
	private Sender workSender;
	
	@Test
    public void oneToMany() throws Exception{
		for (int i=0; i<100; i++){
			workSender.send(Integer.toString(i));
			Thread.sleep(1000);
	    }
	}
}
