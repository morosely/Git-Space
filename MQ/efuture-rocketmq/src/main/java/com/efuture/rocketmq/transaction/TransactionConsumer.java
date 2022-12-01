package com.efuture.rocketmq.transaction;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class TransactionConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new  DefaultMQPushConsumer("Transaction_Consumer");
        consumer.setNamesrvAddr("192.168.84.135:9876");

        // 订阅topic，接收此Topic下的所有消息
        consumer.subscribe("Pay_Topic", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    try {
                        System.out.println(new String(msg.getBody(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}