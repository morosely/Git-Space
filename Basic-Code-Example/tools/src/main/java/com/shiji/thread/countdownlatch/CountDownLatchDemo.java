package com.shiji.thread.countdownlatch;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            log.debug("begin...");
            sleep(1);
            latch.countDown();//用来让计数减一
            log.debug("end...{}", latch.getCount());
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            sleep(2);
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            sleep(1);
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        }).start();

        log.debug("waiting...");
        latch.await();//用来等待计数归零
        log.debug("wait end...");
    }

    public static void sleep(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
