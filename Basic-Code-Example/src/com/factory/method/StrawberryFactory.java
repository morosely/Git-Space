package com.factory.method;

public class StrawberryFactory implements FruitFactory {

	@Override
	public Fruit factory() {
		// TODO Auto-generated method stub
		return new Strawberry();
	}

}
