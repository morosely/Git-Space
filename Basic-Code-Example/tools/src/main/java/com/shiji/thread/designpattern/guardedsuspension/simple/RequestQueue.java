package com.shiji.thread.designpattern.guardedsuspension.simple;

import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue {

    private final Queue<Request> queue = new LinkedList<Request>();
    public synchronized Request getRequest() {
        //如果队列中存在元素，则该方法会返回头元素;如果为空，则返回 nu11。该方法并不移除元素
        while (queue.peek() == null) {
            try {
                //System.out.println(Thread.currentThread().getName() + ": wait() begins, queue = " + queue);
                wait();
                //System.out.println(Thread.currentThread().getName() + ": wait() ends,   queue = " + queue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //该方法用于移除队列的第一个元素，并返回该元素。如果队列中一个元素都没有，则抛出java.util.NoSuchElementException 异常(该元素不存在)。
        return queue.remove();
    }
    public synchronized void putRequest(Request request) {
        queue.offer(request);//该方法用于将元素 req 添加到队列末尾。
        //System.out.println(Thread.currentThread().getName() + ": notifyAll() begins, queue = " + queue);
        notifyAll();
        //System.out.println(Thread.currentThread().getName() + ": notifyAll() ends, queue = " + queue);
     }

}
