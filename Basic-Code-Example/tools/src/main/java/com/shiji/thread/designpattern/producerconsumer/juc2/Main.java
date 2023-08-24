package com.shiji.thread.designpattern.producerconsumer.juc2;

import org.apache.commons.lang.RandomStringUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Exchanger<char[]> exchanger = new Exchanger<char[]>();
        char[] buffer1 = new char[10];
        char[] buffer2 = new char[10];
        new ProducerThread(exchanger, buffer1, 314159).start();
        new ConsumerThread(exchanger, buffer2, 265358).start();
        //test();
    }

    public static void test() throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() ->  {

                try {
                    String origMsg = RandomStringUtils.randomNumeric(6)+ "【" + Thread.currentThread().getName() + "】";
                    System.out.println(Thread.currentThread().getName() + "\t origMsg:" + origMsg );
                    // 先到达的线程会在此等待，直到有一个线程跟它交换数据或者等待超时
                    String exchangeMsg = exchanger.exchange(origMsg,5, TimeUnit.SECONDS);
                    System.out.println(Thread.currentThread().getName() + "\t origMsg:" + origMsg + "\t exchangeMsg:" + exchangeMsg);
                } catch (InterruptedException e) {
                    System.out.print(Thread.currentThread().getName() + "\t 发生InterruptedException异常：");
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    System.out.print(Thread.currentThread().getName() + "\t 发生TimeoutException异常：");
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }

            },String.valueOf(i)).start();
        }
        countDownLatch.await();
    }
}
