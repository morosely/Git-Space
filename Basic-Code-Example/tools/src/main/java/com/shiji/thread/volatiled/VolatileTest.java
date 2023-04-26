package com.shiji.thread.volatiled;

public class VolatileTest {
    private volatile long count = 0;

    private void addCount(){
        count ++;
    }

    public long execute() throws InterruptedException {
        Thread threadA = new Thread(() -> {
            for(int i = 0; i < 1000; i++){
                addCount();
            }
        });
        Thread threadB = new Thread(() -> {
            for(int i = 0; i < 1000; i++){
                addCount();
            }
        });
        //启动线程
        threadA.start();
        threadB.start();
        //等待线程执行完成
        threadA.join();
        threadB.join();
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileTest thread = new VolatileTest();
        long count = thread.execute();
        System.out.println(count);
    }
}