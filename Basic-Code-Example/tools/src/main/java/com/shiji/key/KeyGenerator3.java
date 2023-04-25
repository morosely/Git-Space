package com.shiji.key;

public class KeyGenerator3 {
	
	private static KeyGenerator3 keyGen = new KeyGenerator3();
	private static final int POOLSIZE = 20;
	private KeyInfo info;
	private KeyGenerator3(){
		info = new KeyInfo(POOLSIZE);
	}
	
	public KeyGenerator3 getInstance(){
		return keyGen;
	}
	
	synchronized public int getKeyNext(){
		return info.getKeyNext();
	}
}

class KeyInfo {
	private int keyMax;
	private int keyMin;
	private int keyNext;
	private int poolSize;
	
	public KeyInfo(int poolSize){
		this.poolSize = poolSize;
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
		String updateSQL = "update keyTable set keyValue = keyValue+"+poolSize;
		String selectSQL = "select keyValue form keyTable";
		int keyFromDB = Integer.parseInt(selectSQL);
		keyMax = keyFromDB;
		keyMin = keyFromDB - poolSize + 1;
		keyNext = keyMin;
	}
}