package com.shiji.thread.scheduled;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyScheduledThreadPoolExecutor {

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info(Thread.currentThread().getId()+":"+Thread.currentThread().getName() + "---> 测试ScheduledThreadPoolExecutor");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate( () -> {
                log.info(Thread.currentThread().getId()+":"+Thread.currentThread().getName() + "---> 测试ScheduledThreadPoolExecutor");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }, 1, 1, TimeUnit.SECONDS);

        //主线程休眠10秒
        TimeUnit.SECONDS.sleep(5);
        log.info("正在关闭线程池...");
        // 关闭线程池
        scheduledExecutorService.shutdown();
        boolean isClosed;
        // 等待线程池终止
        do {
            isClosed = scheduledExecutorService.awaitTermination(2, TimeUnit.SECONDS);
            log.info("正在等待线程池中的任务执行完成 isClosed:"+isClosed);
        } while(!isClosed);
        log.info("所有线程执行结束，线程池关闭！可以干其他事情了");

    }

}
