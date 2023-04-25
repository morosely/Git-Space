package com.shiji.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.List;

public class ObjcetProxy implements InvocationHandler{

	private Object real;
	
	public ObjcetProxy(Object real){
		this.real = real;
	}
	
	public static Object factory(Object obj){
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new ObjcetProxy(obj));
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("before calling ..."+method);
		if(args!=null){
			for (int i = 0; i < args.length; i++) {
				System.out.println("args[i] ----- "+args[i]);
			}
		}
		Object o = method.invoke(real, args);
		System.out.println("after calling ..."+method);
		System.out.println();
		return o;
	}
	
	public static void main(String[] args) {
		List v = (List) factory(new Vector());
		v.add("New");
		v.add("York");
	}
}