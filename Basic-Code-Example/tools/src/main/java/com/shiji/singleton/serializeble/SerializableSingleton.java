package com.shiji.singleton.serializeble;

import java.io.Serializable;

public class SerializableSingleton implements Serializable{
	String name;
	
	private SerializableSingleton(){
		System.out.println("SerializableSingleton is created.");
		name = "SerializableSingleton";
	}
	
	private static SerializableSingleton instance = new SerializableSingleton();
	
	public static SerializableSingleton getInstance(){
		return instance;
	}
	
	public static void createString(){
		System.out.println("createString in SerializableSingleton.");
	}
	
	private Object readResolve(){
		System.out.println("readResolve() is invoked. ");
		return instance;
	}
}
