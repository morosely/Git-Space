package com.shiji.thread.pattern;

import lombok.extern.slf4j.Slf4j;

// 停止标记用 volatile 是为了保证该变量在多个线程之间的可见性
// 我们的例子中，即主线程把它修改为 true 对 t1 线程可见
@Slf4j
public class TwoPhaseTerminationVolatile {

    private Thread thread;
    private volatile boolean stop = false;

    public void start(){
        thread = new Thread(() -> {
            while(true) {
                Thread current = Thread.currentThread();
                if(stop) {
                    log.debug("监控结束!");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("程序正在监控中......");
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
                // 执行监控操作
            }
        },"监控线程");
        thread.start();
    }

    public void stop() {
        stop = true;
        thread.interrupt();
    }

    public static void main(String[] args) throws Exception {
        TwoPhaseTerminationVolatile t = new TwoPhaseTerminationVolatile();
        t.start();
        Thread.sleep(5000);
        log.debug("stop");
        t.stop();

    }
}
