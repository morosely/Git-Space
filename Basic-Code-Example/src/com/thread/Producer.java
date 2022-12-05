package com.thread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Producer implements Runnable{

	private boolean isRunning = true;
	private BlockingQueue<PCData> producerQueue;
	private static AtomicInteger count = new AtomicInteger();
	private static final int SLEEPTIME = 1000;
	
	public Producer(BlockingQueue<PCData> queue) {
		this.producerQueue = queue;
	}

	@Override
	public void run() {
		PCData data = null;
		Random r = new Random();
		
		try {
			while(isRunning){
				Thread.sleep(r.nextInt(SLEEPTIME));
				data = new PCData(count.incrementAndGet());
				if(producerQueue.offer(data, 2, TimeUnit.SECONDS)){
					System.out.println("Producer id "+Thread.currentThread().getId()+" ++++++++++ " +data);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		isRunning = false;
	}
}
