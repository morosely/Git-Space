package com.guardedsuspension;

public class ServerThread extends Thread{

	private RequestQueue requestQueue;//请求队列
	
	public ServerThread(RequestQueue requestQueue,String name) {
		super(name);
		this.requestQueue = requestQueue;
	}

	@Override
	public void run() {
		while(true){
			final Request request = requestQueue.getRequest();//得到请求
			System.out.println("final ----------"+request);
			try {
				Thread.sleep(100);//模拟请求超时
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() +" handle "+request);
		}
	}

}
