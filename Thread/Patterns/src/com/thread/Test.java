package com.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {   
        ThreadPoolExecutor ex = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
        
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
         
        for(int i=0;i<20;i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中的线程数目:"+executor.getPoolSize() +" ----- 队列中等待执行的任务数目:"+
            executor.getQueue().size()+" ----- 已执行完别的任务数目:"+executor.getCompletedTaskCount());
        }
        System.out.println("executor.isTerminated() +++++ "+executor.isTerminated());
        executor.shutdown();
        //int j = 1/0;
       /* while(true){
        	 if (executor.getActiveCount() == 0) {
        	        break;
        	 }
        }*/
        while (true) {  
        	System.out.println("executor.isTerminated() ----- "+executor.isTerminated());
            if (executor.isTerminated()) {  
              System.out.println("结束了！");  
              break;  
            }  
            Thread.sleep(200);  
        }  
        System.out.println("===== end =====");
    }
}


class MyTask implements Runnable {
   private int taskNum;
    
   public MyTask(int num) {
       this.taskNum = num;
   }
    
   @Override
   public void run() {
       System.out.println("正在执行task "+taskNum);
       try {
           Thread.currentThread().sleep(100);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       System.out.println("task ------------------------------"+taskNum+"执行完毕");
   }
}
