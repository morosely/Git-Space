package com.shiji.thread.volatiled;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
@Slf4j
public class VolatileRun {

    private static volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException{
        new Thread(()->{
            while (true){
                if(stop){
                    System.out.println("stop...");
                    break;
                }
                //System.out.println("running..."); //加上这个无需volatile ，println方法里synchronized (this)，使得stop可见性
            }
        },"t1").start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("set stop true");
        stop = true;
    }
}
