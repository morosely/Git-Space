package com.shiji.thread.scheduled;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("测试Timer类");
            }
        }, 5000, 1000);
        Thread.sleep(10000);
        timer.cancel();
    }
}
