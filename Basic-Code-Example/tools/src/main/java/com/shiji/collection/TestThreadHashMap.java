package com.shiji.collection;

import java.util.HashMap;
import java.util.UUID;
/**
 * t.join();      //使调用线程 t 在此之前执行完毕。
 * t.join(1000);  //等待 t 线程，等待时间是1000毫秒
 * @author Administrator
 *
 */
/*public class TestThreadHashMap {

	public static void main(String[] args) throws InterruptedException {
		final HashMap<String, String> map = new HashMap<String, String>(2);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							map.put(UUID.randomUUID().toString(), "");
						}
					}, "ftf" + i).start();
				}
			}
		}, "ftf");
		t.start();
		System.out.println("---- before join");
		t.join();
		System.out.println("---- after join");
	}

}*/

public class TestThreadHashMap implements Runnable{  
    
    public static int a = 0;  
  
    public void run() {  
        for (int k = 0; k < 5; k++) {  
        	a = a + 1; 
        	Thread t2 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " --- " +UUID.randomUUID().toString());
				}
			}, "MyThread" + k);
        	t2.start();
        	try {
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        } 
    }  
  
    public static void main(String[] args) throws Exception {  
        Runnable r = new TestThreadHashMap();  
        Thread t = new Thread(r);  
        t.start();
        System.out.println("---- before join");
		t.join();

		System.out.println("---- after join");
        System.out.println(a);  
    }         
}  
