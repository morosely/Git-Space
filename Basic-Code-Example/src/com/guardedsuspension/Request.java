package com.guardedsuspension;

public class Request {

	private String name;

	public Request(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Request [name=" + name + "]";
	}

	public String getName() {
		return name;
	}
	
}
