package com.shiji.thread.delayqueue;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayEvent implements Delayed {
	private Date runTime;
	private int id;
	private static int index = 10;
	
	public DelayEvent(Date runTime,int id) {
		super();
		this.runTime = runTime;
		this.id = id;
	}

	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		if(o==null || !(o instanceof DelayEvent)) return 1;
		if(o==this) return 0;
		DelayEvent de = (DelayEvent)o;
		return this.runTime.compareTo(de.runTime);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		// TODO Auto-generated method stub
//		System.out.println("id:" +id + " =====>>> "+unit.convert(this.runTime.getTime() - System.currentTimeMillis(), TimeUnit.SECONDS));
		return unit.convert(this.runTime.getTime() - System.currentTimeMillis(), TimeUnit.SECONDS);
	}
	
	@Override
	public String toString() {
		return "DelayEvent [runTime=" + runTime + "]  id:"+id;
	}
		
	public void task(DelayQueue<DelayEvent> delayQueue) throws InterruptedException{
		System.out.println("id:"+id+" ===============> task is running ...... queue size is : " +delayQueue.size());
		Thread.sleep(3000);
//		delayQueue.put(new DelayEvent(new Date(new Date().getTime()),index++));
		DelayEvent delayEvent = null;						
		delayEvent = delayQueue.take();
		System.out.println("delayQueue size:"+delayQueue.size() + " ~~~~~~~~~~~~~~~~~~~~>>>>> "+delayEvent);	
	}
	
	/*public static void main(String[] args) {
		Calendar calendar1=Calendar.getInstance();
	    calendar1.set(2017,10,1,1,1,1); //年月日  也可以具体到时分秒如calendar.set(2015, 10, 12,11,32,52); 
	    Date date1=calendar1.getTime();//date就是你需要的时间
	    
	    Calendar calendar2=Calendar.getInstance();
	    calendar2.set(2017,10,1,1,1,0); //年月日  也可以具体到时分秒如calendar.set(2015, 10, 12,11,32,52); 
	    Date date2=calendar2.getTime();//date就是你需要的时间
	    
	    System.out.println(date1.compareTo(date2));
	}*/

}
