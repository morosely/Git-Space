package com.factory.method;

public class AppleFactory implements FruitFactory {

	@Override
	public Fruit factory() {
		// TODO Auto-generated method stub
		return new Apple();
	}

}
