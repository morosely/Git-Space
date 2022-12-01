package com.thread.exception;

import java.util.concurrent.ThreadFactory;

public class HandlerThreadFactory implements ThreadFactory {

	@Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        System.out.println("创建一个新的线程 "+t.getName());
        t.setUncaughtExceptionHandler(new MyUnchecckedExceptionhandler());
        System.out.println("---------==========>>> " + t.getUncaughtExceptionHandler());
        return t;
    }

}
