package com.shiji.thread.pattern;


import lombok.extern.slf4j.Slf4j;

//interrupt 可以打断正在执行的线程，无论这个线程是在 sleep，wait，还是正常运行
@Slf4j
public class TwoPhaseTerminationInterrupt {

    private Thread thread;

    public void start(){
        thread = new Thread(() -> {
            while(true) {
                Thread current = Thread.currentThread();
                if(current.isInterrupted()) {
                    log.debug("监控结束!");
                    break;
                }
                try {
                    log.debug("将结果保存: " + +current.getId() + " " + current.getName());
                    Thread.sleep(10000);
                    log.debug("程序正在监控中......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    current.interrupt();
                }
                // 执行监控操作
            }
        },"goods");
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    public static void main(String[] args) throws Exception {
        TwoPhaseTerminationInterrupt t = new TwoPhaseTerminationInterrupt();
        t.start();
        Thread.sleep(1000);
        //log.debug("stop");
        //t.stop();

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        //activeCount()返回当前正在活动的线程的数量
        int total = Thread.activeCount();
        Thread[] threads = new Thread[total];
        //enumerate(threads)将当前线程组中的active线程全部复制到传入的线程数组threads中
        // 并且返回数组中元素个数，即线程组中active线程数量
        threadGroup.enumerate(threads);
        for (Thread thread : threads) {
            log.info(" -----> "+thread.getId() + " " + thread.getName());
            if("goods".equals(thread.getName())){
                log.info("【matched】=====> "+thread.getId() + " " + thread.getName());
                t.stop();
            }
        }

    }
}

