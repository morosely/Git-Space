package com.packtpub.java7.concurrency.chapter1.recipe1.core;

import java.util.concurrent.CountDownLatch;

import com.packtpub.java7.concurrency.chapter1.recipe1.task.Calculator;

/**
 *  Main class of the example
 */
public class Main {

	/**
	 * Main method of the example
	 * @param args
	 */
	public static void main(String[] args) {
		final int count = 10;
		final CountDownLatch latch = new CountDownLatch(count);
		long start = System.currentTimeMillis();
		
		//Launch 10 threads that make the operation with a different number
		for (int i=1; i<=count; i++){
			Calculator calculator=new Calculator(i,latch);
			Thread thread=new Thread(calculator);
			thread.start();
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();

		System.out.println("Time costs : " + (end - start));
	}
}
