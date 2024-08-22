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
         * 首先，isInterrupted 是实例方法，interrupted 是静态方法，它们的用处都是查看当前打断的状态，但是 isInterrupted 方法查看线程的时候，不会将打断标记清空。
         * interrupted 查看线程打断状态后，会将打断标志置为 false，也就是清空打断标记，简单来说，interrupt() 方法类似于 setter 设置中断值，isInterrupted() 类似于 getter 获取中断值，
         * interrupted() 类似于 getter + setter 先获取中断值，然后清除标志
         */
        Thread t1 = new Thread(() -> {
            log.info("1.--- park");
            LockSupport.park();
            log.info("2.--- unpark");
//            Thread.currentThread().isInterrupted();
//            Thread.interrupted();
              System.out.println("打断状态：isInterrupted() "+ Thread.currentThread().isInterrupted());
              System.out.println("打断状态：interrupted() "+ Thread.interrupted());
//            log.info("3.--- isInterrupted() 打断标记为:{}", Thread.currentThread().isInterrupted());
//            log.info("4.--- interrupted() 打断标记为:{}", Thread.interrupted());
            log.info("5.--- isInterrupted() 打断标记为:{}", Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 使用 Thread.currentThread().isInterrupted() 查看打断标记为 true, LockSupport.park() 失效
            /**
             * 执行结果：
             * 11:54:17 [t1] c.Code_14_Test - park
             * 11:54:18 [t1] c.Code_14_Test - unpark
             * 11:54:18 [t1] c.Code_14_Test - 打断标记为:true
             * 11:54:18 [t1] c.Code_14_Test - unpark
             */
            // 使用 Thread.interrupted() 查看打断标记为 true, 然后清空打断标记为 false, LockSupport.park() 不失效
            /**
             * 执行结果：
             * 11:58:12 [t1] c.Code_14_Test - park
             * 11:58:13 [t1] c.Code_14_Test - unpark
             * 11:58:13 [t1] c.Code_14_Test - 打断标记为:true
             */
            LockSupport.park();
            log.info("6.--- unpark");
        }, "t1");

//        t1.start();
//        Thread.sleep(1000); // 主线程休眠 1 秒
//        t1.interrupt();//interrupt()会设置中断状态为true。注意，interrupt()还会去调用unpark的，所以也会把permit置为1的。
        //LockSupport.unpark(t1);


        /*Thread.currentThread().interrupt();
        LockSupport.park();  //消耗掉permit后，直接返回了
        log.info("a. -----> after park");
        LockSupport.park();  //因为中断状态 == true，直接返回了
        log.info("b. -----> after park");
        LockSupport.park();  //同上
        log.info("c. -----> after park");*/

        /*Thread.currentThread().interrupt();
        try {
            Thread.sleep(1000);  // 消耗掉中断状态后，抛出异常
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.park();  //消耗掉permit
        log.info("a. -----> after park");
        LockSupport.park();  //因为此时permit为0且中断状态为false，所以阻塞
        log.info("b. -----> after park");*/
        test2();
    }

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park();
                log.debug("打断状态：{}", Thread.interrupted());
                log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            }
        });
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
    }

    private static void test2() throws InterruptedException{
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park();
                System.out.println("打断状态：isInterrupted() "+ Thread.currentThread().isInterrupted());
                System.out.println("打断状态：interrupted() "+ Thread.interrupted());
//                log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
//                log.debug("打断状态：{}", Thread.interrupted());
            }
        });
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
    }
}
/*
    park() {
        if(permit > 0) {
            permit = 0;
            return;
        }
        if(中断状态 == true) {
            return;
        }
        阻塞当前线程;  // 将来会从这里被唤醒
        if(permit > 0) {
            permit = 0;
        }
    }

    unpark(Thread thread) {
        if(permit < 1) {
            permit = 1;
            if(thread处于阻塞状态)
                唤醒线程thread;
        }
    }

    interrupt(){
        if(中断状态 == false) {
            中断状态 = true;
        }
        unpark(this);    //注意这是Thread的成员方法，所以我们可以通过this获得Thread对象
    }

    sleep(){//这里我忽略了参数，假设参数是大于0的即可
        if(中断状态 == true) {
            中断状态 = false;
            throw new InterruptedException();
        }

        线程开始睡觉;

        if(中断状态 == true) {
            中断状态 = false;
            throw new InterruptedException();
        }
    }
 */


