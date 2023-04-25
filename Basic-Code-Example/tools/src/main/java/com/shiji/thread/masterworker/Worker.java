package com.shiji.thread.masterworker;

import java.util.Map;
import java.util.Queue;

public abstract class Worker implements Runnable {

	protected Queue<Object> workQueue;
	protected Map<String,Object> resultMap;
	
	public void setWorkQueue(Queue<Object> workQueue) {
		this.workQueue = workQueue;
	}


	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

    //子任务的处理逻辑
	public abstract Object handle(Object input);
	
	@Override
	public void run() {
		while(true){
			//获取子任务
			Object job = workQueue.poll();
			System.out.println(Thread.currentThread().getName()+" ----- "+job);
			if(job == null) break;
			//处理子任务
			Object result = handle(job);
			resultMap.put(Integer.toString(job.hashCode()), result);
		}
	}

}
