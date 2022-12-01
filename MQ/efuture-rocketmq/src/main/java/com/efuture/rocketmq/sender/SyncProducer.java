package com.efuture.rocketmq.sender;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {
	public static void main(String[] args) throws Exception { 
		
		DefaultMQProducer producer = new DefaultMQProducer("My-SyncProducer"); 
		
		producer.setNamesrvAddr("192.168.84.135:9876");
		producer.start();
		
		String msgStr = "用户A发送消息给用户B"; 
		Message msg = new Message("My-Topic","SEND_MSG", msgStr.getBytes(RemotingHelper.DEFAULT_CHARSET));
		
		// 发送消息 
		SendResult sendResult = producer.send(msg); 
		
		System.out.println("消息状态：" + sendResult.getSendStatus()); 
		System.out.println("消息id：" + sendResult.getMsgId());
		System.out.println("消息queue：" + sendResult.getMessageQueue());
		System.out.println("消息offset：" + sendResult.getQueueOffset());
		
		producer.shutdown();
	}
}
