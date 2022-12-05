package com.future;

public class RealData implements Data {

	protected final String result;
	
	public RealData(String param){
		System.out.println(Thread.currentThread().getName()+" ----- realData constructed");
		StringBuffer sb = new StringBuffer();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0;i<10;i++){
			sb.append(param);
		}
		result=sb.toString();
	}
	
	@Override
	public String getResult() {
		return result;
	}

}
