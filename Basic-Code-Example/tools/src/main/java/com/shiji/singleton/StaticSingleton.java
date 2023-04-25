package com.shiji.singleton;

public class StaticSingleton {

	private StaticSingleton() {
		System.out.println("StaticSingleton is created.");
	}

	private static class StaticSingletonHolder{
		private static StaticSingleton instance = new StaticSingleton();
	}
	
	public static StaticSingleton getInstance(){
		return StaticSingletonHolder.instance;
	}
	
	public void createString(){
		System.out.println("createString in Singleton");
	}
	
	private Object readResolve(){
		System.out.println("readResolve() is invoked. ");
		return StaticSingletonHolder.instance;
	}
	
//	public static void main(String[] args) {
//		StaticSingleton staticSingleton = StaticSingleton.getInstance();
//		staticSingleton.createString();
//	}
}
