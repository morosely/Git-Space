package com.shiji.thread.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class SyncAwaitSignal extends ReentrantLock {

    // 循环次数
    private int loopNumber;
    public SyncAwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void start(Condition first) {
        this.lock();
        try {
            System.out.println("start");
            first.signal();
        } finally {
            this.unlock();
        }
    }

    public void print(String str, Condition current, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            this.lock();
            try {
                current.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.unlock();
            }
        }
    }

    public static void main(String[] args) {
        SyncAwaitSignal as = new SyncAwaitSignal(5);
        Condition aWaitSet = as.newCondition();
        Condition bWaitSet = as.newCondition();
        Condition cWaitSet = as.newCondition();
        new Thread(() -> {
            as.print("a", aWaitSet, bWaitSet);
        }).start();
        new Thread(() -> {
            as.print("b", bWaitSet, cWaitSet);
        }).start();
        new Thread(() -> {
            as.print("c", cWaitSet, aWaitSet);
        }).start();
        as.start(aWaitSet);
    }
}
