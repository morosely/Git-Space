package com.shiji.thread.pool.test;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class FourExecutorsCreatePool {

    public static void main(String[] args) throws Exception {
        //newFixed();
        //newSingle();
        synchronousQueue();
        //timer();
        //newScheduled();
        //newScheduleAtFixedRate();
        //newScheduleWithFixedDelay();
    }

    /**
     * 核心线程数 == 最大线程数（没有救急线程被创建），因此也无需超时时间
     * 阻塞队列是无界的，可以放任意数量的任务
     */
    private static void newFixed() {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "mypool_t" + t.getAndIncrement());
            }
        });

        pool.execute(() -> {
            log.debug("1");
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }

    /**
     * 使用场景：希望多个任务排队执行。线程数固定为 1，任务数多于 1 时，会放入无界队列排队。任务执行完毕，这唯一的线程也不会被释放。
     * newFixedThreadPool(1) 和 newSingleThreadExecutor()创建线程池的区别：
     * 1.自己创建一个单线程串行执行任务，如果任务执行失败而终止那么没有任何补救措施，而线程池还会新建一个线程，保证池的正常工作
     * 2.Executors.newSingleThreadExecutor() 线程个数始终为1，不能修改.FinalizableDelegatedExecutorService 应用的是装饰器模式，
     *   只对外暴露了 ExecutorService 接口，因此不能调用 ThreadPoolExecutor 中特有的方法
     * 3.Executors.newFixedThreadPool(1)初始时为1，以后还可以修改对外暴露的是 ThreadPoolExecutor 对象，可以强转后调用 setCorePoolSize 等方法进行修改
     */
    private static void newSingle() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(() -> {
            log.debug("1");
            int i = 1 / 0;
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }

    /**
     * newCachedThreadPool()
     * 核心线程数是 0， 最大线程数是 Integer.MAX_VALUE，救急线程的空闲生存时间是 60s，意味着
     *  1.全部都是救急线程（60s 后可以回收）
     *  2.救急线程可以无限创建
     * 队列采用了 SynchronousQueue 实现特点是，它没有容量，没有线程来取是放不进去的（一手交钱、一手交货）
     */
    private static void synchronousQueue() throws InterruptedException {
        SynchronousQueue<Integer> integers = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                log.debug("putting {} ", 1);
                integers.put(1);
                log.debug("putted {} ...", 1);
                log.debug("putting...{} ", 2);
                integers.put(2);
                log.debug("putted{} ...", 2);
//                log.debug("putting...{} ", 3);
//                integers.put(3);
//                log.debug("putted{} ...", 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                log.debug("taking {}", 1);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                log.debug("taking {}", 2);
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t3").start();
    }


    private static void timer(){
        Timer timer = new Timer();

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 1");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 2");
            }
        };

        // 使用 timer 添加两个任务，希望它们都在 1s 后执行
        // 但由于 timer 内只有一个线程来顺序执行队列中的任务，因此『任务1』的延时，影响了『任务2』的执行
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }


    private static void newScheduled() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("主线程开始启动任务，执行时间：" + sdf.format(new Date()));
        // 添加两个任务，希望它们都在 1s 后执行
        executor.schedule(() -> {
            System.out.println("任务1，执行时间：" + sdf.format(new Date()));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 3000, TimeUnit.MILLISECONDS);

        executor.schedule(() -> {
            System.out.println("任务2，执行时间：" + sdf.format(new Date()));
        }, 3000, TimeUnit.MILLISECONDS);


    }

    private static void newScheduleAtFixedRate() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("start...");
        //由于任务执行时间 > 间隔时间，间隔被『撑』到了 2s
        pool.scheduleAtFixedRate(() -> {
            log.debug("running...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 3, 1, TimeUnit.SECONDS);
    }

    private static void newScheduleWithFixedDelay(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("start...");
        //一开始，延时 3s，scheduleWithFixedDelay 的间隔是 上一个任务结束 <-> 延时 <-> 下一个任务开始 所以间隔都是 3s
        pool.scheduleWithFixedDelay(()-> {
            log.debug("running...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 3, 1, TimeUnit.SECONDS);

    }
}
