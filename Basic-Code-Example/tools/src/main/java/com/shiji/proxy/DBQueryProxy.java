package com.shiji.proxy;

public class DBQueryProxy implements IDBQuery {

	private DBQuery real;
	
	@Override
	public String request(String content,Integer id) {
		preReuqst();
		
		if(real == null){
			real = new DBQuery();
		}
		String result = real.request(content,id);
		
		postRequest();
		return result;
	}
	
	private void postRequest() {
		System.out.println("after request__________");
	}

	private void preReuqst() {
		System.out.println("before request__________");
	}

}
