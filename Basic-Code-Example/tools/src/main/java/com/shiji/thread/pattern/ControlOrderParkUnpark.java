package com.shiji.thread.pattern;

import java.util.concurrent.locks.LockSupport;

public class ControlOrderParkUnpark {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
            // 当没有『许可』时，当前线程暂停运行；有『许可』时，用掉这个『许可』，当前线程恢复运行
            LockSupport.park();
            System.out.println("1");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("2");
            // 给线程 t1 发放『许可』（多次连续调用 unpark 只会发放一个『许可』）
            LockSupport.unpark(t1);
        });

        t1.start();
        t2.start();
    }
}
