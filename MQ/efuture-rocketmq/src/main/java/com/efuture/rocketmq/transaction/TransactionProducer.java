package com.efuture.rocketmq.transaction;

import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

public class TransactionProducer {

	public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("Transaction_Producer");
        producer.setNamesrvAddr("192.168.84.135:9876");

        // 设置事务监听器
        producer.setTransactionListener(new LocalTransactionListener());
        producer.start();

        // 发送消息
        Message message = new Message("Pay_Topic", "用户A给用户B转账500元".getBytes("UTF-8"));
        producer.sendMessageInTransaction(message, null);

        Thread.sleep(999999);
        producer.shutdown();
    }
}
