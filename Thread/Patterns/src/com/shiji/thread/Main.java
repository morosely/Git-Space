package com.shiji.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * Main class of the example. Launch three tasks using the invokeAll() method
 * and then prints their results to the console
 *
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {

		// Create an executor
		ExecutorService executor=(ExecutorService)Executors.newCachedThreadPool();

		// Create three tasks and stores them in a List
		List<Task> taskList = null;
		// Call the invokeAll() method
		List<Future<Result>>resultList=null;
		for (int j=0; j<5; j++){
			taskList=new ArrayList<>();
			for (int i=0; i<10; i++){
				Task task=new Task("Task:"+j+"-"+i);
				taskList.add(task);
			}
	
			try {
				resultList=executor.invokeAll(taskList);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Writes the results to the console
			System.out.printf("====================== Core: Printing the results\n");
			for (int i=0; i<resultList.size(); i++){
				Future<Result> future=resultList.get(i);
				try {
					Result result=future.get();
					System.out.printf("%s: %s\n",result.getName(),result.getValue());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				} 
			}
			System.out.println("====================== Batch "+j+" Task End");
		}
		// Finish the executor
//		executor.shutdown();
		
//		List<String> data = new ArrayList<String>();
//		data.add("1");data.add("2");data.add("3");data.add("4");data.add("5");data.add("6");data.add("7");data.add("8");data.add("9");data.add("10");
//		data.add("11");data.add("12");data.add("13");data.add("14");data.add("15");data.add("16");data.add("17");data.add("18");data.add("19");data.add("20");
//		data.add("21");data.add("22");data.add("23");data.add("24");data.add("25");data.add("26");data.add("27");data.add("28");data.add("29");data.add("30");
//		data.add("31");
//		//每一次处理的数据量
//		Integer batchCount = 9;
//		//需要处理的次数
//		Integer times = (data.size() + batchCount - 1 ) / batchCount ;
//		
//		for (int i = 0; i < times; i++) {
//			List subList = data.subList(i * batchCount, i == (times - 1) ? data.size() : i * batchCount + batchCount);
//			System.out.println("start =============================="+i * batchCount +" : " + (i == (times - 1) ? data.size() : i * batchCount + batchCount));
//			System.out.println(subList);
//			System.out.println("end ==============================" +i * batchCount +" : " + (i == (times - 1) ? data.size() : i * batchCount + batchCount));
//			System.out.println();
//		}
		
	}

}
