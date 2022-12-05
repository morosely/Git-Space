package com.string;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

	private Object real;
	
	public MyInvocationHandler(Object obj){
		this.real = obj;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		long startTime = System.currentTimeMillis();
		Object obj = method.invoke(real, args);
		long endTime = System.currentTimeMillis();
		System.out.println(method.getName()+"() costs time is :"+(endTime - startTime));
		return obj;
	}

}
