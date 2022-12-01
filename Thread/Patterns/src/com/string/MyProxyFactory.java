package com.string;

import java.lang.reflect.Proxy;

public class MyProxyFactory {
	//为指定的target生成代理对象
	public static Object getProxy(Object target){
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),new MyInvocationHandler(target));
	}
	
}
