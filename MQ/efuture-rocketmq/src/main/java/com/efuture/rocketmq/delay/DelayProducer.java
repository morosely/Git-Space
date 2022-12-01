package com.efuture.rocketmq.delay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class DelayProducer {

    public static void main(String[] args) throws MQClientException,RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("Delay-Producer-Group");
        producer.setNamesrvAddr("192.168.84.135:9876");
        producer.start();
        Message message = null;
        for (int i = 0; i < 20; i++) {
            // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message = new Message("Delay-Topic", ("hello shiji - " +i).getBytes());
            // 设置延迟级别，0表示不延迟，大于18的总是延迟2h
            message.setDelayTimeLevel(i);
            producer.send(message);
        }
        producer.shutdown();
    }

}
