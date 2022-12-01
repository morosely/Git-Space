package com.efuture.rocketmq.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class OrderProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("Producer-Order");
        producer.setNamesrvAddr("192.168.84.135:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            int orderId = i % 10; // 模拟生成订单id
            String msgStr = "orderMessage --> " + i +", orderId = "+ orderId;
            Message message = new Message("Order-Topic", "Order-Msg", msgStr.getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message, (mqs, msg, arg) -> {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }, orderId);
            System.out.println(sendResult);
        }
        producer.shutdown();
    }

}
