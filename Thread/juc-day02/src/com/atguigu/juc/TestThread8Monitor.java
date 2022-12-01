package com.atguigu.juc;

import java.util.concurrent.TimeUnit;

/*
 * 题目：判断打印的 "one" or "two" ？
 * 
 * 1. 两个普通同步方法，两个线程，标准打印， 打印? //one  two
 * 2. 新增 Thread.sleep() 给 getOne() ,打印? //one  two
 * 3. 新增普通方法 getThree() , 打印? //three  one   two
 * 4. 两个普通同步方法，两个 Number 对象，打印?  //two  one
 * 5. 修改 getOne() 为静态同步方法，打印?  //two   one
 * 6. 修改两个方法均为静态同步方法，一个 Number 对象?  //one   two
 * 7. 一个静态同步方法，一个非静态同步方法，两个 Number 对象?  //two  one
 * 8. 两个静态同步方法，两个 Number 对象?   //one  two
 * 
 * 线程八锁的关键：
 * ①非静态方法的锁默认为  this,  静态方法的锁为 对应的 Class 实例
 * ②某一个时刻内，只能有一个线程持有锁，无论几个方法。
 */
public class TestThread8Monitor {
	
	public static void main(String[] args) {
		final Number number = new Number();
		final Number number2 = new Number();
		number.getOne();
		number.getOne();
		new Thread(new Runnable() {
			@Override
			public void run() {
				//number.getOne();
			} 
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				//number2.getOne();
//				number.getTwo();
//				number2.getOne();
			}
		}).start();
		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				number.getThree();
//			}
//		}).start();
		
//		final Person person = new Person();
//		final Person person2 = new Person();
//		person.method1();
//		person2.method2();
//		
//		System.out.println("-------------------");
//		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				person.method1();
//			} 
//		}).start();
//		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				person2.method2();
//			} 
//		}).start();
	}

}

class Number{
	
	public /*static synchronized*/ void getOne(){//Number.class
		System.out.println("one ----- start" );
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
		}
		System.out.println("one ----- end");
	}
	
	public synchronized void getTwo(){//this
		System.out.println("two");
	}
	
	public void getThree(){
		System.out.println("three");
	}
	
}

class Person{
	
	public synchronized void method1(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		System.out.println("=====>>> method1");
	}
	
	public synchronized void method2(){
		System.out.println("=====>>> method2");
	}
}