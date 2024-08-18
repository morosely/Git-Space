package com.shiji.thread.completablefuture;

import java.util.concurrent.*;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = ThreadLocalRandom.current().nextInt(10);
            if(result >= 5){
                int i = result / 0;
            }
            System.out.println("-----> 1秒钟后结果：result = " + result);
            return result;
        },threadPool).whenComplete((v,e) ->{
            if(e==null){
                System.out.println("-----> 计算完成。更新数据库value = " + v);
            }
        }).exceptionally((e)->{
            e.printStackTrace();
            System.out.println("-----> 系统产生异常："+e.getCause()+"\t"+e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName()+":线程开始做其他任务...");
        threadPool.shutdown();
    }
}
