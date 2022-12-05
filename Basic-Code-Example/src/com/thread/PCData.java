package com.thread;

public final class PCData {

	private final int intData;

	public PCData(int intData) {
		this.intData = intData;
	}

	@Override
	public String toString() {
		return intData+"";
	}

	public int getIntData() {
		return intData;
	}
}
