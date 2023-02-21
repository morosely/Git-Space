package com.shiji.thread.status;

public class BlockedThread implements Runnable {
    @Override
    public void run() {
        synchronized (BlockedThread.class){
            while (true){
                WaitingTime.waitSecond(100);
            }
        }
    }
}
