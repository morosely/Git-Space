package com.thread;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Main {

	public static void main(String[] args) throws InterruptedException, ParseException {
        /*BlockingQueue<PCData> queue = new LinkedBlockingQueue<PCData>(10);
        //建立生产者
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        //建立消费者
        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);
        Consumer consumer3 = new Consumer(queue);
        //线程池
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer1);
        service.execute(consumer2);
        service.execute(consumer3);
        Thread.sleep(10 * 1000);
        //停止生产者
        producer1.stop();
        producer2.stop();
        producer3.stop();
        Thread.sleep(3000);
        service.shutdown();*/
		
		/*long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			new Thread(new MyThread("NoThreadPool "+i)).start();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("NoThreadPool costs time ---------- "+(endTime - startTime));
		
		long startTime2 = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			ThreadPool.getInstance().start(new MyThread("ThreadPool "+i));
			System.out.println("ThreadPool size ---------- "+ThreadPool.getInstance().getCreatedThreadsCount());
		}
		long endTime2 = System.currentTimeMillis();
		System.out.println("ThreadPool costs time ---------- "+(endTime2 - startTime2));*/
		
		// 创建 Calendar 对象  
	    Calendar calendar = Calendar.getInstance();
	    //字符串转换日期格式  
	    DateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    // 得到日期格式对象  
	    Date date = fmtDateTime.parse("2015-02-12 08:08:08"); 
	    calendar.setTime(date);  
	    
	    //初始化 Calendar 对象，但并不必要，除非需要重置时间  
	    System.out.println(calendar.get(calendar.MONTH)+1);
	    System.out.println(calendar.get(calendar.DAY_OF_MONTH));
	    
    }

}
