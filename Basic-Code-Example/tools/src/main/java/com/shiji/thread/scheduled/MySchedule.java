package com.shiji.thread.scheduled;

import cn.hutool.core.date.DateUtil;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MySchedule {

    public static void main(String[] args) {
            ScheduledExecutorService respScheduler = new ScheduledThreadPoolExecutor(2);
            System.out.println("task begin: " + DateUtil.date(System.currentTimeMillis()));
            //scheduleAtFixedRate属于固定速率的
            //period为固定周期时间，按照一定频率来重复执行任务。
            //如果period设置的是3秒，系统执行要5秒；那么等上一次任务执行完就立即执行，也就是任务与任务之间的差异是5s；
            //如果period设置的是3s，系统执行要2s；那么需要等到3S后再次执行下一次任务。
            /*respScheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);//2000
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" ---> task run: "+DateUtil.date(System.currentTimeMillis()));
                }
            },1,3, TimeUnit.SECONDS);*/

            //如果设置的period为3s;任务执行耗时为5S那么下次任务执行时间为第8S。
            /*respScheduler.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" ---> task run: "+DateUtil.date(System.currentTimeMillis()));
                }
            },1,3, TimeUnit.SECONDS);*/

            //schedule属于固定延迟的
            respScheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" ---> task run: "+DateUtil.date(System.currentTimeMillis()));
                }
            },3,TimeUnit.SECONDS);
    }
}
