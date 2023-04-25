package com.shiji.proxy;

public class Main {

	public static void main(String[] args) {
		//IDBQuery query = new DBQueryProxy();
		//query.request();
		
		IDBQuery q = JDKDBQueryHandler.createJDKDBQueryProxy();
		q.request("AAA",200);
	}

}
