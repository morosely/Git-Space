package com.guardedsuspension;

public class Main {

	public static void main(String[] args) {
		RequestQueue requestQueue = new RequestQueue();
		
		for (int i = 0; i < 5; i++) {
			new ServerThread(requestQueue,"ServerThread - "+i).start();
		}
		
		for (int i = 0; i < 5; i++) {
			new ClientThread(requestQueue,"CientThread - "+i).start();
		}
		
		//System.out.printf("%s: %d * %d = %d\n","Result",2,3,2*3);
	}

}
