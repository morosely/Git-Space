package com.thread.delayqueue;

import java.util.concurrent.DelayQueue;

public class Main {

	private static volatile DelayQueue<DelayEvent>  queue = new DelayQueue<DelayEvent>();
	
	public static void main(String[] args) throws InterruptedException {
		 Thread threads[] = new Thread[5];
		 for (int i = 0; i < threads.length; i++) {
			 DelayTask task = new DelayTask(queue,i);
			 threads[i]=new Thread(task);
		 }
		 for (int i = 0; i < threads.length; i++) {
			 threads[i].start();
		 }
		 for (int i = 0; i < threads.length; i++) {
	            try {
	                threads[i].join();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
		do {
			DelayEvent delayEvent = null;						
			delayEvent = queue.take();
			if(delayEvent != null){
				delayEvent.task(queue);
			}else{
				System.out.println("queue size:"+queue.size() +" ==========> "+delayEvent);				
			}
			Thread.sleep(100);
		} while (queue.size()>0);
		
	}
}
