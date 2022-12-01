package com.packtpub.java7.concurrency.chapter1.recipe12.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

	private static ThreadLocal<Date> date= new ThreadLocal<Date>() {
		protected Date initialValue(){
			return new Date();
		}
	};
	
	//private Date date;
	
	@Override
	public void run() {
		//date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(Thread.currentThread().getName()+" --- "+sf.format(date.get()));
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" --- "+sf.format(date.get()));
	}

}
