package com.guardedsuspension;

public class ClientThread extends Thread {
	
	private RequestQueue requestQueue;
	
	public ClientThread(RequestQueue requestQueue,String name) {
		super(name);
		this.requestQueue = requestQueue;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			Request request = new Request("Request ID :"+i+" --- Thread Name : "+Thread.currentThread().getName());//构造请求
			System.out.println(Thread.currentThread().getName() + " requests "+request);
			requestQueue.addRequest(request);//提交请求
			try {
				Thread.sleep(10);//客户端的请求速度快于服务器端请求速度
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Client name is : "+Thread.currentThread().getName());
		}
		System.out.println(Thread.currentThread().getName() + " request end...");
	}
}
