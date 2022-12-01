package com.efuture.rocketmq;
import org.apache.rocketmq.client.producer.DefaultMQProducer; 
import org.apache.rocketmq.client.producer.SendResult; 
import org.apache.rocketmq.common.message.Message; 
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class ProducerDemo {

	public static void main(String[] args) throws Exception { 
		//Instantiate with a producer group name. 
		DefaultMQProducer producer = new DefaultMQProducer("Producer-Demo");
		//Specify name server addresses. 
		producer.setNamesrvAddr("192.168.84.135:9876");
		//Launch the instance. 
		producer.start(); 
		for (int i = 0; i < 5; i++) {
			//Create a message instance, specifying topic, tag and message body.
			Message msg = null;
			if( i % 3 == 0) {
				msg = new Message("Topic-Demo" /* Topic */, "delete" /* Tag */,
						("Hello RocketMQ - Tags " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */);
			}else{
				msg = new Message("Topic-Demo" /* Topic */,
						("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */);
			}
			//Call send message to deliver message to one of brokers. 
			SendResult sendResult = producer.send(msg);
			System.out.printf("%s%n", sendResult); 
		}
		//Shut down once the producer instance is not longer in use. 
		producer.shutdown(); 
	}
	
}
