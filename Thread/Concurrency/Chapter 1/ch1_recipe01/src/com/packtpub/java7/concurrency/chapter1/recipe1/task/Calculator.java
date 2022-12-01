package com.packtpub.java7.concurrency.chapter1.recipe1.task;

import java.util.concurrent.CountDownLatch;

/**
 *  This class prints the multiplication table of a number
 */
public class Calculator implements Runnable {

	/**
	 *  The number
	 */
	private int number;
	
	/**
	 *  Constructor of the class
	 * @param number : The number
	 */
	public Calculator(int number) {
		this.number=number;
	}
	
	private CountDownLatch latch;
	public Calculator(int number,CountDownLatch latch) {
		this.latch = latch;
		this.number=number;
	}
	/**
	 *  Method that do the calculations
	 */
	@Override
	public void run() {
		try {
			for (int i=1; i<=10; i++){
				System.out.printf("%s: %d * %d = %d\n",Thread.currentThread().getName(),number,i,i*number);
			} 
		}finally {
			latch.countDown();
		}
	}

}
