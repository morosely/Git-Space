package com.key;

public class KeyGenerator2 {

	private static KeyGenerator2 keyGen = new KeyGenerator2();
	
	private KeyGenerator2() {
		
	}
	
	public static KeyGenerator2 getInstance(){
		return keyGen;
	}
	
	public synchronized int getKeyValue(){
		return getKeyValueFromDB();
	}

	private int getKeyValueFromDB() {
		String updateSQL = "update keyTable set keyValue = keyValue+1";
		String selectSQL = "select keyValue form keyTable";
		return Integer.parseInt(selectSQL);
	}

}
