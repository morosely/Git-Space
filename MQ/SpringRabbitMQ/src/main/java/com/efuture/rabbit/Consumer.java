package com.efuture.rabbit;

public class Consumer {

	//具体执行业务的方法
    public void listen(String message) {
        System.out.println("\n消费者： " + message + "\n");
    }
}
