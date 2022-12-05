package com.factory.method;

public class ConcreteCreator1 implements Creator {

	@Override
	public Product factory() {
		// TODO Auto-generated method stub
		return new ConcreteProduct1();
	}

}
