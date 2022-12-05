package com.listen;

public class MyListener implements Listenable {

	@Override
	public void enventChanged(MyEvnet e) {
		System.out.println("value   changed   to:   " + e.getValue());
	}
}