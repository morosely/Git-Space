package com.shiji.thread.async;

import java.util.concurrent.*;

/**
 * 测试FutureTask获取异步结果
 */
public class FutureTaskTest {

    public static void main(String[] args)throws ExecutionException, InterruptedException{
        //结合Thread类的使用示例
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "测试FutureTask获取异步结果";
            }
        });
        new Thread(futureTask).start();
        System.out.println(futureTask.get());

        //结合线程池的使用示例
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "测试FutureTask获取异步结果";
            }
        });
        executorService.execute(futureTask);
        System.out.println(futureTask.get());
        executorService.shutdown();
    }
}