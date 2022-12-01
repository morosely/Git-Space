package com.future;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Client {

	public Data request(final String query){
		final FutrueData futrueData = new FutrueData();
		new Thread(){
			@Override
			public void run() {
				RealData realData = new RealData(query);
				futrueData.setRealData(realData);
			}
		}.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" ----- continue");
		return futrueData;
	}
	
	public static void main(String[] args){
		
		//1.
		/*Client client = new Client();
		Data data = client.request("abc");
		System.out.println(Thread.currentThread().getName()+" ----- "+data.getResult());*/
		
		//2.jdk
		/*FutureTask<String> future = new FutureTask<String> (new RealDataCallable("A"));
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(future);//client.request("abc");
		for(int i=0;i<1000;i++){
			if(i%100 == 0){
				System.out.println(Thread.currentThread().getName() + " : "+i);
			}
		}
		try {
			System.out.println(Thread.currentThread().getName()+" ----- "+future.get());//data.getResult()
		} catch (InterruptedException e) {
			future.cancel(true);// 处理中断异常
			e.printStackTrace();
		} catch (ExecutionException e) {
			future.cancel(true);// 处理无法执行任务异常
			e.printStackTrace();
		}finally{
			executor.shutdown();//关闭线程池

		}*/
		
	}
	
}
	
