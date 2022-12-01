package com.packtpub.java7.concurrency.chapter1.recipe7.task;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class that shows the usage of ThreadLocal variables to share
 * data between Thread objects
 *
 */
public class SafeTask implements Runnable {

	/**
	 * ThreadLocal shared between the Thread objects
	 */
	private static ThreadLocal<Date> startDate= new ThreadLocal<Date>() {
		protected Date initialValue(){
			return new Date();
		}
	};
	
	private static ThreadLocal<Integer> count= new ThreadLocal<Integer>() {
		protected Integer initialValue(){
			Integer i= new Integer(0);
			return ++i;
		}
	};

	/**
	 * Main method of the class
	 */
	@Override
	public void run() {
		// Writes the start date
		//System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(),startDate.get());
		System.out.println(Thread.currentThread().getName()+" ¡¾Start¡¿ ----- "+count.get());
		try {
			TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Writes the start date
		//System.out.printf("Thread Finished: %s : %s\n",Thread.currentThread().getId(),startDate.get());
		System.out.println(Thread.currentThread().getName()+" ¡¾Finished¡¿ ********** "+count.get());
	}

}
