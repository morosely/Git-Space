package com.shiji.proxy;

public class DBQuery implements IDBQuery{

	public DBQuery() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String request(String content,Integer id) {
		System.out.println("DB request......"+id + " ===>>> "+content);
		return "DB Requst Return...";
	}

}
