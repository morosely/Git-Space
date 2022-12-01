package com.listen;

import java.util.Date;

public class Main {

	public static void main(String[] args) {
		MySource ms = new MySource();
		MyListener ml = new MyListener();
		ms.addListener(ml);
		ms.setValue(10);
		String.format("Created thread %d with name %s on %s\n",1,"张三",new Date());
		System.out.printf("%s: %d * %d = %d\n",Thread.currentThread().getName(),2,3,2*3);
	}

}
