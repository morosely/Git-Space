package com.efuture.rocketmq.topic;
import org.apache.rocketmq.client.exception.MQClientException; 
import org.apache.rocketmq.client.producer.DefaultMQProducer; 
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

public class MyTopic {

	public static void main(String[] args) throws Exception {
		DefaultMQProducer producer = new DefaultMQProducer("My-Producer");
		producer.setNamesrvAddr("192.168.84.135:9876");
		producer.start();
		/**
		 * key：broker名称
		 * newTopic：topic名称
		 * queueNum：队列数（分区）
		 */
		producer.createTopic("broker-1", "My-Topic", 8);
		System.out.println("创建topic成功");
		producer.shutdown();

	}
}
