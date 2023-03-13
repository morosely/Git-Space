package com.shiji.thread.interrupt;

import java.util.concurrent.TimeUnit;

public class InterruptSleepThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                Thread currentThread = Thread.currentThread();
                int count = 0 ;
                while (true) {
                    if (currentThread.isInterrupted()) {
                        System.out.println(currentThread.getId() + ": Interrupted!");
                        break;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                        System.out.println(currentThread.getId() + ": running ---> " + (++count));
                    } catch (InterruptedException e) {
                        System.out.println(currentThread.getId() + ": Interrupted When Sleep");
                        //设置中断状态
                        //注意: Thread.sleep() 方法由于中断而抛出异常，此时，它会清除中断标记，如果不加处理，那么在下一次循环开始时，就无法捕获这个中断，故在异常处理中，再次设置中断标记位。
                        currentThread.interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t1.start();
        TimeUnit.MILLISECONDS.sleep(5000);
        System.out.println("主线程:【"+Thread.currentThread().getId()+"】开始调用子线程【"+t1.getId()+"】的interrupt()方法");
        t1.interrupt();
    }
}
