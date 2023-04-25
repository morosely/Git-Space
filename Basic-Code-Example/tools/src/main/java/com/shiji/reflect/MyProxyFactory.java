package com.shiji.reflect;

import java.lang.reflect.Proxy;

public class MyProxyFactory {
	//为指定的target生成代理对象
	public static Object getProxy(Object target){
		MyInvocationHandler handler = new MyInvocationHandler();
		handler.setTarget(target);
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),handler);
	}
	
}
