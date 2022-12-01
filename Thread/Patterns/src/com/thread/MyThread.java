package com.thread;

//定义一个线程类，作为任务对象
public class MyThread implements Runnable {

	protected String name;
	
	public MyThread(String name) {
		System.out.println(name +" is created...");
		this.name = name;
	}

	@Override
	public void run() {
		
		try {
			Thread.sleep(100);//使用一个sleep代替具体你功能的执行
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
