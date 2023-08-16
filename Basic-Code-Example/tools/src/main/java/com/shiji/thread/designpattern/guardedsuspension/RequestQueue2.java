package com.shiji.thread.designpattern.guardedsuspension;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestQueue2 {

    /**
     * 由于take 方法和put 方法已经考虑了互斥处理，所以 getRequest 方法和putRequest 方法也就无需声明为synchronized方法。
     * LinkedBlockingQueue类中使用了GuardedSuspension模式，能够保证线安全
     */
    private final BlockingQueue<Request> queue = new LinkedBlockingQueue<Request>();
    public Request getRequest() {
        Request req = null;
        try {
            req = queue.take();//取出队首元素
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return req;
    }
    public void putRequest(Request request) {
        try {
            queue.put(request);//向队列末尾添加元素
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
