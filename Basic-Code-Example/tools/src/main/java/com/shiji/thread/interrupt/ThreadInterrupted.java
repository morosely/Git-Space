package com.shiji.thread.interrupt;

import java.util.concurrent.TimeUnit;

class MyThread1 extends Thread{
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + "正在打印 i = " + i);
        }
    }
}

class MyThread2 extends Thread{
    @Override
    public void run() {
        //如何才能中断mt线程呢？
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + "正在打印 i = " + i);
            if(this.isInterrupted()){//检查中断状态
                System.out.println("通过this.isInterrupted()检测到中断");
                System.out.println("=====>>>>> 第一个interrupted()，值为：" + this.interrupted());
                System.out.println("=====>>>>> 第二个interrupted()，值为：" + this.interrupted());
                break;
            }
        }
    }
}

public class ThreadInterrupted {
    public static void main(String[] args) throws InterruptedException {
        //test1();
        test2();
    }

    private static void test2() throws InterruptedException {
        MyThread2 mt2 = new MyThread2();
        mt2.start();
        mt2.interrupt();
        //Thread.currentThread().interrupt();
        System.out.println("第一次调用isInterrupted()方法，值为：" + mt2.isInterrupted());
        System.out.println("第二次调用isInterrupted()方法，值为：" + mt2.isInterrupted());
        System.out.println("调用interrupted()方法，值为：" + mt2.interrupted());//interrupted()方法测试的是当前线程是否被中断。但是这里的当前线程是main线程，而mt.interrupted()中断的是mt1线程，而不是main线程。
        System.out.println("调用interrupted()方法，值为：" + mt2.interrupted());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("thread是否存活：" + mt2.isAlive());
    }
    private static void test1() {
        MyThread1 mt1 = new MyThread1();
        mt1.start();
        mt1.interrupt();
        //Thread.currentThread().interrupt();
        System.out.println("第一次调用isInterrupted()方法，值为：" + mt1.isInterrupted());
        System.out.println("第二次调用isInterrupted()方法，值为：" + mt1.isInterrupted());
        System.out.println("调用interrupted()方法，值为：" + mt1.interrupted());//interrupted()方法测试的是当前线程是否被中断。但是这里的当前线程是main线程，而mt.interrupted()中断的是mt1线程，而不是main线程。
        System.out.println("调用interrupted()方法，值为：" + mt1.interrupted());
        System.out.println("thread是否存活：" + mt1.isAlive());
    }
}

/*
 *  interrupted()作用于当前线程，interrupt（）和isInterrupted（）作用于此线程，即代码中调用此方法的实例所代表的线程。
 */