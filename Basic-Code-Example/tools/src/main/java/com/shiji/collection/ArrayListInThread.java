package com.shiji.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*public class ArrayListInThread implements Runnable {
 //    List<String> list = new ArrayList<String>();// not thread safe

 List<String> list = Collections.synchronizedList(new ArrayList<String>());// thread safe

 public void run() {
 try {
 Thread.sleep((int)(Math.random() * 2));
 }
 catch (InterruptedException e) {
 e.printStackTrace();
 }
 list.add(Thread.currentThread().getName());
 }

 public static void main(String[] args) throws InterruptedException {
 ThreadGroup group = new ThreadGroup("mygroup");
 ArrayListInThread t = new ArrayListInThread();
 for (int i = 0; i < 10000; i++) {
 Thread th = new Thread(group, t, String.valueOf(i));
 th.start();
 }

 while (group.activeCount() > 0) {
 Thread.sleep(10);
 }
 System.out.println("list size : "+t.list.size()); // it should be 10000 if thread safe collection is used.
 }

 }*/

public class ArrayListInThread {
	public static void main(String[] args) {
//		final List list = new ArrayList();
//		final List list = new Vector();
		final List list = Collections.synchronizedList(new ArrayList());
		for (int i = 0; i < 10000; i++) {
			list.add(i);
		}
		list.add(10,"insert");
		/*for (int i = 0; i < 10; i++)
			new Thread() {
				@Override
				public void run() {
					for (int i = 0; i < 1000; i++)
						list.remove(0);
				}
			}.start();*/

		/*for (int i = 0; i < 10; i++)
			new Thread() {
				@Override
				public void run() {
					for (int i = 0; i < 1000; i++)
						list.add(10000 * i + i);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}.start();*/
			
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		System.out.println(list.size());
	}
}