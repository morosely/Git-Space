package com.efuture.rocketmq.balance;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

public class BalanceConsumer {

    public static void main(String[] args) throws Exception {

        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("Balance-Consumer-Group");
        consumer.setNamesrvAddr("192.168.84.135:9876");
        consumer.start();
        //消费某topic主题指定的MQ
        final PullResult pullResult = consumer.pull(new MessageQueue("Balance-Topic", "broker-1", 5),
                "*",
                0L,
                10);
        System.out.println(pullResult);
        List<MessageExt> msgList = pullResult.getMsgFoundList();
        if(msgList != null && !msgList.isEmpty()) {
            msgList.forEach(messageExt -> {
                //System.out.println(messageExt);
                try {
                    System.out.println("消息:" + new String(messageExt.getBody(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
        }
        consumer.shutdown();
    }

}