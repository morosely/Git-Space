package com.shiji.simple.factory;

public class Client {

	public static void main(String[] args) {
		try {
			FruitFactory.factory("apple").grow();
			FruitFactory.factory("grape").grow();
			FruitFactory.factory("strawberry").grow();
			FruitFactory.factory("pears").grow();
		} catch (BadFruitException e) {
			e.printStackTrace();
		}
	}
}
