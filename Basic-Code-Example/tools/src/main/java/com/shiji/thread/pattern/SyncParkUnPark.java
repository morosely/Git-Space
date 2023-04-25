package com.shiji.thread.pattern;

import java.util.concurrent.locks.LockSupport;

public class SyncParkUnPark {

    private int loopNumber;
    public SyncParkUnPark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String content,Thread nextThread) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(content);
            LockSupport.unpark(nextThread);
        }
    }

    static Thread t1;
    static Thread t2;
    static Thread t3;
    public static void main(String[] args) {
        SyncParkUnPark syncPark = new SyncParkUnPark(5);
        t1 = new Thread(() -> {
            syncPark.print("a",t2);
        });
        t2 = new Thread(() -> {
            syncPark.print("b",t3);
        });
        t3 = new Thread(() -> {
            syncPark.print("c",t1);
        });

        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);

    }
}
