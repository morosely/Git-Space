package com.efuture.rocketmq.delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class DelayConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new
                DefaultMQPushConsumer("Delay-Consumer-Group");
        consumer.setNamesrvAddr("192.168.84.135:9876");
        consumer.subscribe("Delay-Topic", "*");
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus
            consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(System.currentTimeMillis() / 1000);
                for (MessageExt msg : msgs) {
                    System.out.println(
                            msg.getTopic() + "\t"
                                    + msg.getQueueId() + "\t"
                                    + msg.getMsgId() + "\t"
                                    + msg.getDelayTimeLevel() + "\t"
                                    + new String(msg.getBody())
                    );
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
