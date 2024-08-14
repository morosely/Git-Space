package com.shiji.thread.pool;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException, IOException {
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 0L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(4);//（最大任务数+阻塞队列长度）- 总执行任务数=被拒绝的任务
        //**流程**：**先给核心池，核心池满了则将任务放进阻塞队列(阻塞队列满了，才会去判断当前总任务数与最大线程池数的关系，若小于，则会new线程去执行（与核心池一样，阻塞队列的则等待被执行）；若大于maxsize，则拒绝执行)**
        ThreadFactory threadFactory = new NameTreadFactory();
        RejectedExecutionHandler handler = new MyIgnorePolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        executor.prestartAllCoreThreads(); // 预启动所有核心线程

        for (int i = 1; i <= 10; i++) {
            MyTask task = new MyTask(String.valueOf(i));
            executor.execute(task);
        }

        System.in.read(); //阻塞主线程
        //**结果解释**：8个被执行2个被拒绝，其中，创建了4个线程Thread（最大池数），先执行4个任务，5s后最后一波任务一次性执行4个；
        //**前2个任务直接进入核心池，后来的4个进入阻塞队列，再来了2个（2核心+2=4max）则new新线程去执行，最后俩因为超过max限制了，被reject**；
    }

    static class NameTreadFactory implements ThreadFactory { //定义线程创建的工厂**
        private final AtomicInteger mThreadNum = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    public static class MyIgnorePolicy implements RejectedExecutionHandler {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }
        private void doLog(Runnable r, ThreadPoolExecutor e) {
            //** 自定义拒绝策略，可做日志记录等**
            System.err.println(r.toString() + " rejected");
            //System.out.println("completedTaskCount: " + e.getCompletedTaskCount());
        }
    }

    static class MyTask implements Runnable {
        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.toString() + " is running!");
                Thread.sleep(5000); //**让任务执行慢点**
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }

    }
}