package com.thread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
	
	private BlockingQueue<PCData> queue;
	private static final int SLEEPTIME=1000;
	
	public Consumer(BlockingQueue<PCData> queue){
		this.queue = queue;
	}

	@Override
	public void run() {
		
		Random r = new Random();
		try {
			while(true){
				PCData data = queue.take();
				if(data!=null){
					int re = data.getIntData();
					System.out.println("Consumer id "+Thread.currentThread().getId()+" ---------- "+ re);
					Thread.sleep(r.nextInt(SLEEPTIME));
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 
	}
}
