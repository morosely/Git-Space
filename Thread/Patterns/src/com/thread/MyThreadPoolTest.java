package com.thread;

public class MyThreadPoolTest {

    public static void main(String[] args) {
        MyThreadPool threadPool = new MyThreadPool(4); //创建一个有个4工作线程的线程池 
        try {
            Thread.sleep(1000);   //休眠1秒,以便让线程池中的工作线程全部运行
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0; i < 7; i++) {  //创建6个任务
            threadPool.execute(createTask(i));
        }
        threadPool.waitFinish();  //等待所有任务执行完毕  
        threadPool.closePool();   //关闭线程池  
    }
    
    private static Runnable createTask(final int taskID){
        return new Runnable() {
            
            @Override
            public void run() {
                System.out.println("Hello world!");
            }
        };
    }

}
