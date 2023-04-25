package com.shiji.thread.exception;

public class ThreadException2 implements Runnable {
	 
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        t.setName("AAA");
        System.out.println("run() by ---------->>> " + t);
        System.out.println("---------->>> " + t.getUncaughtExceptionHandler());
        throw new RuntimeException("抛出运行时异常");
    }
}