package com.future;

import java.util.concurrent.Callable;

public class RealDataCallable implements Callable<String> {

	protected final String result;
	
	public RealDataCallable(String param){
		result = param;
	}
	
	@Override
	public String call() throws Exception {
		//System.out.println(Thread.currentThread().getName()+" ----- call() is invoked");
		StringBuffer sb = new StringBuffer();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0;i<10;i++){
			if(i%100 == 0){
				System.out.println(Thread.currentThread().getName()+ " : "+i);
			}
			sb.append(result);
		}
		return sb.toString();
	}

}
