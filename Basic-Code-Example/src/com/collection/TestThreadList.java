package com.collection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程ConcurrentModificationException异常
 * 
 * @author Administrator
 * 
 */
public class TestThreadList {

	public static void main(String[] args) throws Exception{

		//1. 多线程的异常情况
		/*final List<String> myList = new ArrayList<String>();
		myList.add("1");
		myList.add("2");
		myList.add("3");
		myList.add("4");
		myList.add("5");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (String string : myList) {
					System.out.println("遍历集合 value = " + string);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (Iterator<String> it = myList.iterator(); it.hasNext();) {
					String value = it.next();
					if (value.equals("3")) {
						it.remove();
						System.out.println("删除元素 value = " + value);
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();*/
		
		//2.解决方案
		/*(1) 在所有遍历增删地方都加上synchronized或者使用Collections.synchronizedList，虽然能解决问题但是并不推荐，因为增删造成的同步锁可能会阻塞遍历操作。
		  (2) 推荐使用ConcurrentHashMap或者CopyOnWriteArrayList。
		  CopyOnWriteArrayList注意事项
		  (1) CopyOnWriteArrayList不能使用Iterator.remove()进行删除。
		  (2) CopyOnWriteArrayList使用Iterator且使用List.remove(Object);会出现如下异常：
		  java.lang.UnsupportedOperationException: Unsupported operation remove
		    at java.util.concurrent.CopyOnWriteArrayList$ListIteratorImpl.remove(CopyOnWriteArrayList.java:804)*/
		
		 CountDownLatch latch=new CountDownLatch(2);//两个工人的协作
		
		 List<String> myList  = new CopyOnWriteArrayList<String>();  
		 myList.add( "1");  
		 myList.add( "2");  
		 myList.add( "3");  
		 myList.add( "4");  
		 myList.add( "5");  
		 WorkerLook worker1=new WorkerLook(latch,myList);  
		 WorkerRemove worker2=new WorkerRemove(latch,myList);  
	     worker1.start();
	     worker2.start(); 
	     latch.await();//等待所有工人完成工作 
		 System.out.println(myList);
	}
}

class WorkerLook extends Thread{
	CountDownLatch latch;
	List<String> myList;
	public WorkerLook(CountDownLatch latch,List<String> myList){
		this.latch = latch;
		this.myList = myList;
	}
	@Override  
	public void run() {
		try {
			for (String string : myList) {
				System.out.println("遍历集合 value = " + string);
				Thread.sleep(100);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			latch.countDown();// 工人完成工作，计数器减一
		}
	}
}

class WorkerRemove extends Thread{
	CountDownLatch latch;
	List<String> myList;
	public WorkerRemove(CountDownLatch latch,List<String> myList){
		this.latch = latch;
		this.myList = myList;
	}
    @Override  
    public void run() { 
		try {
			for (int i = myList.size() - 1; i >= 0; i--) {
				String value = myList.get(i);
				if (value.equals("3")) {
					System.out.println("删除元素 value = " + value);
					myList.remove(value);
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			latch.countDown();// 工人完成工作，计数器减一
		}
    }  
}