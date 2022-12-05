package com.thread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
	
	public static void main(String[] args) {
		int count = 10;
		final CountDownLatch latch = new CountDownLatch(count);
		for (int i = 1; i <= count; i++) {
			Calculator calculator = new Calculator(latch);
			Thread thread = new Thread(calculator);
			thread.start();
		}
		
		try {
			latch.await(1,TimeUnit.SECONDS);
			System.out.println("==========> Main End ==========>");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Calculator implements Runnable {

	private CountDownLatch latch;

	public Calculator(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
			System.out.println(Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			latch.countDown();
		}
	}
}