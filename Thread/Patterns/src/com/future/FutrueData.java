package com.future;

public class FutrueData implements Data {

	protected RealData realData;
	protected boolean isReady;//真是数据是否注入
	
	public synchronized void setRealData(RealData realData){
		System.out.println(Thread.currentThread().getName()+" ----- FutrueData setRealData ...");
		if(isReady){
			return;
		}
		this.realData = realData;
		isReady = true;
		notifyAll();
	}
	
	@Override
	public synchronized String getResult() {
		try {
			while(!isReady){
				System.out.println(Thread.currentThread().getName()+" ----- FutrueData getResult() 1 ...");
				wait();
				System.out.println(Thread.currentThread().getName()+" ----- FutrueData getResult() 2 ...");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return realData.getResult();
	}

}
