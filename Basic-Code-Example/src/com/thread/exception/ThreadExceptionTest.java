package com.thread.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExceptionTest {
	public static void main(String[] args) {
        //下面有3中方式来执行线程。
        //第1种按照普通的方式。这是能捕获到异常
//        Thread t = new Thread(new ThreadException2());
//        t.setUncaughtExceptionHandler(new MyUnchecckedExceptionhandler());
//        t.start();
        
        //第2种按照现成池，直接按照thread方式,此时不能捕获到异常，为什么呢？因为在下面代码中创建了一个线程，且设置了异常处理器，
        //但是呢，在我们线程池中会重设置新的Thread对象，而这个Thread对象没有设置任何异常处理器，换句话说，我们在线程池外对线程做的
        //任何操作都是没有用的
//        ExecutorService exec1 = Executors.newCachedThreadPool();
//        Runnable runnable = new ThreadException2();
//        Thread t1 = new Thread(runnable);
//        t1.setUncaughtExceptionHandler(new MyUnchecckedExceptionhandler());
//        exec1.execute(runnable);
//        exec1.shutdown();
        
        //第3种情况一样的，也是走的线程池，但是呢是通过ThreadFactory方式，在ThreadFactory中会对线程做一些控制，可以设置异常处理器
        //此时是可以捕获异常的。
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ThreadException2());
        exec.shutdown();
        
    }
}
