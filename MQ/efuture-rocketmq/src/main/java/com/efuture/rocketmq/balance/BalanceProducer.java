package com.efuture.rocketmq.balance;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

public class BalanceProducer {

    public static void main(String[] args) throws Exception{

        /**
         *  -b,--brokerAddr <arg>       create topic to which broker
         *  -c,--clusterName <arg>      create topic to which cluster
         *  -h,--help                   Print help
         *  -n,--namesrvAddr <arg>      Name server address list, eg: 192.168.0.1:9876;192.168.0.2:9876
         *  -o,--order <arg>            set topic's order(true|false)
         *  -p,--perm <arg>             set topic's permission(2|4|6), intro[2:W 4:R; 6:RW]
         *  -r,--readQueueNums <arg>    set read queue nums
         *  -s,--hasUnitSub <arg>       has unit sub (true|false)
         *  -t,--topic <arg>            topic name
         *  -u,--unit <arg>             is unit topic (true|false)
         *  -w,--writeQueueNums <arg>   set write queue nums
         * 创建主题 6个 MessageQueue
         * mqadmin updateTopic -n localhost:9876 -t goods_topic -w 6 -b localhost:10911
         */
        DefaultMQProducer producer = new DefaultMQProducer("Balance-Producer-Group");
        producer.setNamesrvAddr("192.168.84.135:9876");
        producer.start();
        Message message = new Message();
        message.setTopic("Balance-Topic");
        message.setBody("My goods name is '小米手机12 Pro'".getBytes());
        //发送某topic主题指定的MQ
        SendResult result = producer.send(message,
                new MessageQueue("goods_topic", "broker-1", 5),
                1000
        );
        System.out.println(result.getSendStatus());
        producer.shutdown();
    }
}
