package com.shiji.key;

public class KeyGenerator1 {

	private static KeyGenerator1 keyGen = new KeyGenerator1();
	private int keyValue = 1000;
	
	private KeyGenerator1(){
		
	}
	
	public static KeyGenerator1 getInstance(){
		return keyGen;
	}
	
	public synchronized int getKeyValue(){
		return keyValue++;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KeyGenerator1 keyGen = KeyGenerator1.getInstance();
		System.out.println(keyGen.getKeyValue());
		System.out.println(keyGen.getKeyValue());
		System.out.println(keyGen.getKeyValue());
	}

}
