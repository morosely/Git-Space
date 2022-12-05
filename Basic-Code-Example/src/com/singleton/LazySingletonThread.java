package com.singleton;

public class LazySingletonThread implements Runnable {

	@Override
	public void run() {
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			LazySingleton.getInstance();
		}
		System.out.println(Thread.currentThread().getName() +" LazySingleton spend time --------------------"+(System.currentTimeMillis() - beginTime));
	}

}
