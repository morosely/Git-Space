package com.shiji.thread.pool.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPool {

    public final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    public static final int COUNT_BITS = Integer.SIZE - 3;
    public static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    public static final int RUNNING    = -1 << COUNT_BITS;
    public static final int SHUTDOWN   =  0 << COUNT_BITS;
    public static final int STOP       =  1 << COUNT_BITS;
    public static final int TIDYING    =  2 << COUNT_BITS;
    public static final int TERMINATED =  3 << COUNT_BITS;

    // Packing and unpacking ctl
    public static int runStateOf(int c)     { return c & ~CAPACITY; }
    public static int workerCountOf(int c)  { return c & CAPACITY; }
    public static int ctlOf(int rs, int wc) { return rs | wc; }

    public static final byte status_run = -1 << 4;

    /*
     * Bit field accessors that don't require unpacking ctl.
     * These depend on the bit layout and on workerCount being never negative.
     */

    private static boolean runStateLessThan(int c, int s) {
        return c < s;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    public static void main(String[] args) {
//        binary(1 << COUNT_BITS);
//        binary(CAPACITY);
//        binary(RUNNING);
//        binary(SHUTDOWN);
//        binary(STOP);
//        binary(TIDYING);
//        binary(TERMINATED);
          binary(ctlOf(MyThreadPool.RUNNING, 0));
//        binary8(status_run);
//          Thread t1 = new Tread1();
//          Thread t2 = new Tread2();
//          t1.start();
//          t2.setDaemon(true);
//          t2.start();
    }


    public static void binary(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }

    public static void binary8(int num) {
        for (int i = 7; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }
}

class Tread1 extends Thread{
    @Override
    public void run() {
       for(int i = 0; i < 5; i++){
           try {
               TimeUnit.SECONDS.sleep(2);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           System.out.println(Thread.currentThread().getId() + ": i=" + i);
       }
    }
}

class Tread2 extends Thread{
    @Override
    public void run() {
        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId()+": "+"守护线程");
        }
    }
}
