package com.shiji.thread.delayqueue;

import java.util.Date;
import java.util.concurrent.DelayQueue;

public class DelayTask implements Runnable {

	private DelayQueue delayQueue;
	private int id;
	
	public DelayTask(DelayQueue delayQueue,int id){
		this.delayQueue = delayQueue;
		this.id = id;
	}
	
	@Override
	public void run() {
		Date date = new Date();
		delayQueue.put(new DelayEvent(new Date(new Date().getTime()+id*1000),id));
	}

}
