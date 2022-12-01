package com.singleton;

public class LazySingleton {
	private static LazySingleton instance = null;
	
	private LazySingleton(){
		System.out.println("LazySingleton is created.");
	}
	
	public static synchronized LazySingleton getInstance(){
		if(instance == null){
			instance = new LazySingleton();
		}
		return instance;
	}
	
	public static void createString(){
		System.out.println("createString in Singleton");
	}
	
}
