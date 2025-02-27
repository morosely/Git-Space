package com.shiji.thread.designpattern.workthread.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.Random;

public class ClientThread extends Thread {
    private final ExecutorService executorService;
    private static final Random random = new Random();
    public ClientThread(String name, ExecutorService executorService) {
        super(name);
        this.executorService = executorService;
    }
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Request request = new Request(getName(), i);
                executorService.execute(request);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
            System.out.println(getName() + " : " + e);
        }
    }
}
