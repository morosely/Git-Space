package com.shiji.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDBQueryHandler implements InvocationHandler {

	private DBQuery real;
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		preReuqst();
		
		if(real == null){
			real = new DBQuery();
		}
		String result = real.request("ABC",100);
		
		postRequest();
		return result;
	}

	private void postRequest() {
		System.out.println("after request......");
	}

	private void preReuqst() {
		System.out.println("before request......");
	}
	
	
	public static IDBQuery createJDKDBQueryProxy(){
		IDBQuery proxy = (IDBQuery)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{IDBQuery.class},new JDKDBQueryHandler());
		return proxy;
	}
}
