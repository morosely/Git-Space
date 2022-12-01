package com.efuture.rocketmq.retry;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ConsumerRetry {

	public static void main(String[] args) throws Exception { 
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Retry-Comsumer"); 
		consumer.setNamesrvAddr("192.168.84.135:9876");
		// 订阅topic，接收此Topic下的所有消息 
		consumer.subscribe("Retry-Topic", "*");
		// 设置重新消费的次数.消息超过重试次数依然没有消费成功，进入死信队列
		consumer.setMaxReconsumeTimes(1);
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override 
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) { 
				for (MessageExt msg : msgs) {
					System.out.println("消费的重试次数: "+msg.getReconsumeTimes());
					try {
						System.out.println(new String(msg.getBody(), "UTF-8"));
						//System.out.println(msg);
//						if(msg.getReconsumeTimes() >= 3){ // 重试3次后，不再进行重试
//							return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//						}
					} catch (UnsupportedEncodingException e) { 
						e.printStackTrace();
					}
				}
				
				System.out.println("收到消息->" + msgs);
				return ConsumeConcurrentlyStatus.RECONSUME_LATER; } 
			}); 
		consumer.start();
	}
}
