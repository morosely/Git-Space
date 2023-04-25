package com.shiji.reflect;

public class DogTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dog target = new GunDog();
		Dog proxyDog = (Dog)MyProxyFactory.getProxy(target);
		proxyDog.info();
		System.out.println();
		proxyDog.run();
	}

}
