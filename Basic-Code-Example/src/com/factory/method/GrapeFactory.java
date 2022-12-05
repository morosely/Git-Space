package com.factory.method;

public class GrapeFactory implements FruitFactory {

	@Override
	public Fruit factory() {
		// TODO Auto-generated method stub
		return new Grape();
	}

}
