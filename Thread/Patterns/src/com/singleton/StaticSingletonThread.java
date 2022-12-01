package com.singleton;

public class StaticSingletonThread implements Runnable {

	@Override
	public void run() {
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			StaticSingleton.getInstance();
		}
		System.out.println(Thread.currentThread().getName() +" StaticSingleton spend time --------------------"+(System.currentTimeMillis() - beginTime));
	}

}
