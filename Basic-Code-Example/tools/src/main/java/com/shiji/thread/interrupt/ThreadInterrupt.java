package com.shiji.thread.interrupt;

public class ThreadInterrupt {

    public static void main(String args[]){
        Thread thread = new MyThread();
        thread.start();
        try {
            Thread.sleep(100);
            thread.interrupt();
            //System.out.println("===================>>>1: thread.interrupted():" + thread.interrupted());
            //System.out.println("===================>>>2: thread.interrupted():" + thread.interrupted());
            System.out.println("===================>>>3: thread.isInterrupted():" + thread.isInterrupted());
            System.out.println("===================>>>4: thread.isInterrupted():" + thread.isInterrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThread extends Thread {
    public void run(){
        super.run();
        for(int i=0; i<10000; i++){
            if(this.isInterrupted()) {
                System.out.println("线程已经终止， for循环不再执行 ---> 中断标志位："+this.isInterrupted());
                //break;
                return;
            }
            System.out.println("i="+(i+1) + " ---> 中断标志位："+Thread.currentThread().isInterrupted());
        }
        System.out.println("这是for循环外面的语句，也会被执行"+" ---> 中断标志位："+this.isInterrupted());
    }
}

