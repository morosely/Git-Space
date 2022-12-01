package com.singleton;

public class EagerSingleton {
	private static final EagerSingleton instance = new EagerSingleton();
	
	private EagerSingleton(){
		System.out.println("EagerSingleton is created.");
	}
	
	public static EagerSingleton getInstance(){
		return instance;
	}
	
	public static void createString(){
		System.out.println("createString in Singleton");
	}
	
	public static void main(String[] args) {
		LazySingleton.createString();
	}
}
