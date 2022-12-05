package com.singleton;

public class EagerSingletonThread implements Runnable {

	@Override
	public void run() {
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			EagerSingleton.getInstance();
		}
		System.out.println(Thread.currentThread().getName() +" EagerSingleton spend time ----------"+(System.currentTimeMillis() - beginTime));
	}

}
