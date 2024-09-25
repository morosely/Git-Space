package com.shiji.thread.threadlocal;

public class ThreadLocalTest {

//    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    private static ThreadLocal<String> threadLocal = new InheritableThreadLocal<String>();

    public static void main(String[] args){
        threadLocal.set("主线程:【" + Thread.currentThread().getName()+"】");

        //创建第一个线程
        Thread threadA = new Thread(()->{
            //threadLocal.set("ThreadA【" + Thread.currentThread().getName()+"】");
            System.out.println("线程A本地变量中的值为 ===》" + threadLocal.get());
            threadLocal.remove();
        },"A");
        //创建第二个线程
        Thread threadB = new Thread(()->{
            threadLocal.set("ThreadB【" + Thread.currentThread().getName()+"】");
            System.out.println("线程B本地变量中的值为 ===》" + threadLocal.get());
            threadLocal.remove();
        },"B");

        //启动线程A和线程B
        threadA.start();
        threadB.start();
        System.out.println("主线程main本地变量中的值为 ===》" + threadLocal.get());
    }
}
