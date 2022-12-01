package com.key;

public class KeyInformation {
	private int keyMax;
	private int keyMin;
	private int keyNext;
	private int poolSize;
	private String keyName;
	
	public KeyInformation(int poolSize,String keyName){
		this.poolSize = poolSize;
		this.keyName = keyName;
		retrieveFromDB();
	}
	
	public int getKeyMax(){
		return keyMax;
	}
	
	public int getKeyMin(){
		return keyMin;
	}
	
    public int getKeyNext(){
		if(keyNext>keyMax){
			retrieveFromDB();
		}
		return keyNext++;
	}
	
	public void retrieveFromDB(){
		String updateSQL = "update keyTable set keyValue = keyValue+"+poolSize+" where keyName = "+keyName;
		String selectSQL = "select keyValue form keyTable where keyName = "+keyName;
		int keyFromDB = Integer.parseInt(selectSQL);
		keyMax = keyFromDB;
		keyMin = keyFromDB - poolSize + 1;
		keyNext = keyMin;
	}

	public void retrieveFromDBVersion(){
		int tryTime = 3;
		int count = 0;
		do {
			count++;
			String version = "select keyValue,version form keyTable where keyName = "+keyName;
			String updateSQL = "update keyTable set keyValue = keyValue+"+poolSize+", version = version + 1 where keyName = "+keyName+ "and version =" +version;
			if(Integer.parseInt(updateSQL) == 1) {
				int keyFromDB = Integer.parseInt(version);
				keyMax = keyFromDB;
				keyMin = keyFromDB - poolSize + 1;
				keyNext = keyMin;
			}else if(Integer.parseInt(updateSQL) == 0 && count > tryTime){
				System.err.println("尝试三次失败");
				break;
			}
		} while (true);
	}
}