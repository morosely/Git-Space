package com.oberver;

public class ConcreteOberver implements IOberver {

	private String observerState;
	@Override
	public void update(String state) {
		this.observerState = state;
		System.out.println("I am notified......state is "+observerState);
	}

}
