package com.shiji.simple.factory;

public class Apple implements Fruit {

	private int treeAge;
	
	@Override
	public void grow() {
		log("Apple is growing.");
	}

	@Override
	public void harvest() {
		log("Apple has been harvested.");
	}

	@Override
	public void plant() {
		log("Apple has been planted.");
	}

	public static void log(String message){
		System.out.println(message);
	}

	public int getTreeAge() {
		return treeAge;
	}

	public void setTreeAge(int treeAge) {
		this.treeAge = treeAge;
	}
	
	
}
