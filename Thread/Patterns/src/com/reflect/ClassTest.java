package com.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassTest {
	private ClassTest(){
		System.out.println("ClassTest() is running");
	}
	public ClassTest(String info){
		System.out.println("ClassTest(String name) is running : "+info);
	}
	
	public void info(){
		System.out.println("info() is running");
	}
	
	public void info(String name){
		System.out.println("info(String name) is running : "+name);
	}
	
	class Inner{
		
	}
	
	private int age;
	private void setAge(int age){
		System.out.println("setAge(int age) is running : "+age);
		this.age = age;
	}
	
	private int getAge(){
		System.out.println("getAge() is running");
		return age;
	}
	

	public static void main(String[] args) throws Exception{
		Class clazz = ClassTest.class;
		Constructor [] cons = clazz.getDeclaredConstructors();
		for(Constructor c : cons){
			System.out.println(c);
		}
		System.out.println("1 ----------");
		Method[] methods = clazz.getDeclaredMethods();
		for(Method m:methods){
			System.out.println(m);
		}
		System.out.println("2 ----------");
		Method m = clazz.getMethod("info", String.class);
		m.invoke(clazz.newInstance(), "123");
		System.out.println("3 ----------");
		Field[] fs = clazz.getDeclaredFields();
		for(Field f:fs){
			System.out.println(f);
		}
	}
}
