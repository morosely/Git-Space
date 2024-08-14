package com.shiji.thread.daemon;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.concurrent.TimeUnit;

public class LogMonitor implements Runnable {

    private volatile boolean running = true;

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        while (running) {
            long usedMemory = memoryBean.getHeapMemoryUsage().getUsed();
            System.out.printf("Current heap memory usage: %d bytes%n", usedMemory);

            try {
                TimeUnit.SECONDS.sleep(5); // 每5秒检查一次
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 保留中断状态
                break;
            }
        }
        System.out.println("Log Monitor thread is stopping.");
    }

    public static void main(String[] args) {
        Thread logMonitorThread = new Thread(new LogMonitor());
        logMonitorThread.setDaemon(true); // 设置为守护线程
        logMonitorThread.start();

        // 主线程逻辑
        for (int i = 0; i < 10; i++) {
            System.out.println("Main thread working...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Main thread finished.");
    }
}

//LogMonitor类：实现Runnable接口，包含一个stop方法用于停止线程，以及一个run方法，后者是守护线程执行的逻辑，每5秒打印一次堆内存使用情况。
//main方法：创建LogMonitor实例，并将其包装成一个线程，通过setDaemon(true)将其设置为守护线程，然后启动。主线程则进行一个简单的循环模拟工作，每次循环睡眠1秒，共循环10次，之后结束。
//此示例中，当主线程执行完毕后，JVM会自动终止，此时守护线程也会随之停止。如果需要手动停止守护线程，可以在适当的时机调用LogMonitor实例的stop方法。
