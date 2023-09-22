package com.shiji.thread.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureAndCallableExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**

        // 1. 使用 FutureTask 传入 Callable 接口方式创建
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyCallable());
        // 2. 传入 future, 因为 FutureTask 这个类是实现了 RunnableFuture 接口，RunnableFuture 继承了 Runnable 接口
        Thread t1 = new Thread(futureTask, "t1");
        t1.start();
        // 3. 获取返回结果时
        // 当主线程获取 t1 线程的返回值时, 需要等 2 秒，此时主线程进入阻塞状态
        log.debug("{}",  futureTask.get());
         */

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 使用 Callable ，可以获取返回值
        Callable<String> callable = () -> {
            log.info("entry Callable method call()...");
            // 模拟子线程任务，在此睡眠 2s，
            // 小细节：由于 call 方法会抛出 Exception，这里不用像使用 Runnable 的run 方法那样 try/catch 了
            for (int i=0; i<100; i++){
                log.info("Thread is running now 【"+i+"】");
                Thread.sleep(1000);
            }
            return "Hello from Callable";
        };

        FutureTask<String> futureTask = new FutureTask<String>(callable);
        executorService.submit(futureTask);
        log.info("已提交任务到线程池");
        //Future<String> future = executorService.submit(callable);

        log.info("主线程继续执行");
        log.info("主线程等待获取 Future 结果");
        // 如果子线程没有结束，则睡眠 1s 重新检查
        long startTime = System.nanoTime();
        while(!futureTask.isDone()) {
            log.info("子线程任务还没有结束...");
            Thread.sleep(1000);
            double elapsedTimeInSec = (System.nanoTime() - startTime)/1000000000.0;
            // 如果程序运行时间大于 3s，则取消子线程的运行
            if(elapsedTimeInSec > 3) {
                futureTask.cancel(true);
                //break;
            }
        }

        // Future.get() blocks until the result is available
        //String result = future.get();
        //log.info("主线程获取到 Future 结果: {}", result);

        // 通过 isCancelled 方法判断程序是否被取消，如果被取消，则打印日志，如果没被取消，则正常调用 get() 方法
        if (!futureTask.isCancelled()){
            try{
                String result = futureTask.get();
                log.info("主线程获取到 Future 结果: {}", result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            log.warn("子线程任务被取消");
        }

        executorService.shutdown();
    }

}