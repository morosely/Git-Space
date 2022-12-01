package com.efuture.rocketmq;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class ConsumerDemo {

	public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Consumer-Demo");
        consumer.setNamesrvAddr("192.168.84.135:9876");

        // 订阅消息，接收的是所有消息
        //consumer.subscribe("Topic-Demo", "*");
        //consumer.subscribe("Topic-Demo", "add || update");
        consumer.subscribe("Topic-Demo", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
        	@Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt msg : msgs) {
                        System.out.println(" * 消息:" + new String(msg.getBody(), "UTF-8"));
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //System.out.println("接收到消息 -> " + msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者
        consumer.start();
    }
}
