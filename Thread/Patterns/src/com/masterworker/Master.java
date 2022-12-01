package com.masterworker;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
	//队列任务
	protected Queue<Object> workQueue = new ConcurrentLinkedQueue<Object>(); 

	//进程队列
	protected Map<String,Thread> threadMap = new HashMap<String,Thread>();

	//子任务处理结果集
	protected Map<String,Object> resultMap = new ConcurrentHashMap<String, Object>();
	
	//Master构成。需要一个worker进程和所需要的进程数量
	public Master(Worker worker,int countWorker) {
		worker.setWorkQueue(workQueue);
		worker.setResultMap(resultMap);
		for (int i = 1; i <= countWorker; i++) {
			threadMap.put(Integer.toString(i), new Thread(worker,"worker"+String.format("%02d",i)));
		}
	}
	
	//是否所有的子任务结束
	public boolean isCompleted(){
		for(Map.Entry<String, Thread> entry : threadMap.entrySet()){
			Thread currentThread = entry.getValue();
			if(currentThread.getState()!=Thread.State.TERMINATED){
				return false;
			}
		}
		return true;
	}
	
	//开始运行所有的worker进程
	public void execute(){
		for (Map.Entry<String, Thread> entry : threadMap.entrySet() ) {
			Thread currentThread = entry.getValue();
			currentThread.start();
		}
	}
	
	//提交一个任务
	public void submit(Object job){
		workQueue.add(job);
	}

	//返回子任务处理集
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	

	
}
