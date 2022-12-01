package com.efuture.rabbitmq.work;

import java.io.IOException;

import com.efuture.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class WorkRecv1 {
	private final static String QUEUE_NAME = "work_queue";

    public static void main(String[] argv) throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
                // 休眠1秒
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                System.out.println(" [消费者 1] received : " + msg + "!");
                // 手动进行ACK
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        
        // 监听队列，第二个参数：是否自动进行消息确认。
        //channel.basicConsume(QUEUE_NAME, true, consumer);
        
        // 监听队列，第二个参数false，手动进行ACK
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
