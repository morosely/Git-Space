package com.shiji.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 测试 isInterrupted 与 interrupted
 */
@Slf4j
public class ThreadInterrupt {

    public static void main(String args[]) throws InterruptedException {
        /**
         * isInterrupted() 与 interrupted() 比较，如下：
         * 首先，isInterrupted 是实例方法，interrupted 是静态方法，它们的用处都是查看当前打断的状态，但是 isInterrupted 方法查看线程的时候，不会将打断标记清空，也就是置为 false，
         * interrupted 查看线程打断状态后，会将打断标志置为 false，也就是清空打断标记，简单来说，interrupt() 方法类似于 setter 设置中断值，isInterrupted() 类似于 getter 获取中断值，
         * interrupted() 类似于 getter + setter 先获取中断值，然后清除标志
         */
        Thread t1 = new Thread(() -> {
            log.info("park");
            LockSupport.park();
            log.info("unpark");
            log.info("打断标记为:{}", Thread.currentThread().isInterrupted());
            // 使用 Thread.currentThread().isInterrupted() 查看打断标记为 true, LockSupport.park() 失效
            /**
             * 执行结果：
             * 11:54:17 [t1] c.Code_14_Test - park
             * 11:54:18 [t1] c.Code_14_Test - unpark
             * 11:54:18 [t1] c.Code_14_Test - 打断标记为:true
             * 11:54:18 [t1] c.Code_14_Test - unpark
             */
            //log.info("打断标记为:{}", Thread.interrupted());
            // 使用 Thread.interrupted() 查看打断标记为 true, 然后清空打断标记为 false, LockSupport.park() 不失效
            /**
             * 执行结果：
             * 11:58:12 [t1] c.Code_14_Test - park
             * 11:58:13 [t1] c.Code_14_Test - unpark
             * 11:58:13 [t1] c.Code_14_Test - 打断标记为:true
             */
            LockSupport.park();
            log.info("unpark");

        }, "t1");

        t1.start();
        Thread.sleep(1000); // 主线程休眠 1 秒
        t1.interrupt();
    }

}


