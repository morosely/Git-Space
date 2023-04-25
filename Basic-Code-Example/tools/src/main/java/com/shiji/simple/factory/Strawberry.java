package com.shiji.simple.factory;

public class Strawberry implements Fruit {

	@Override
	public void grow() {
		log("Strawberry is growing.");
	}

	@Override
	public void harvest() {
		log("Strawberry has been harvested.");
	}

	@Override
	public void plant() {
		log("Strawberry has been planted.");
	}

	public static void log(String message){
		System.out.println(message);
	}
}
