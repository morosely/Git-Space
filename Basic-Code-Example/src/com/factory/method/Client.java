package com.factory.method;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Client {

	public static void main(String[] args) throws IOException {
		Creator c1 = new ConcreteCreator1();
		Product p1 = c1.factory();
		Creator c2 = new ConcreteCreator2();
		Product p2 = c2.factory();
		
		Fruit apple = new AppleFactory().factory();
		Fruit grape = new GrapeFactory().factory();
		Fruit strawberry = new StrawberryFactory().factory();
		
	    apple.grow();
	    grape.grow();
	    strawberry.grow();
	    
	    URL yahoo = new URL("www.yahoo.com");
	    URLConnection con = yahoo.openConnection();
	}
	
}
