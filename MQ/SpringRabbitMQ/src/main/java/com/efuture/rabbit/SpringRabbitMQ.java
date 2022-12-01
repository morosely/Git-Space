package com.efuture.rabbit;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRabbitMQ {
	public static void main(final String... args) throws Exception {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/rabbitmq-context.xml");
        AmqpTemplate template = context.getBean(AmqpTemplate.class);
        template.convertAndSend("fanoutExchangeQueue", "HelloWorld");
        //String comsumer = (String) template.receiveAndConvert("fanoutExchangeQueue");
        context.close(); //容器销毁
    }
}
