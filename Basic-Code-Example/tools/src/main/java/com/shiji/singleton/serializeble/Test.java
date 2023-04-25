package com.shiji.singleton.serializeble;

import java.io.IOException;

public class Test {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		SerializableSingleton s = SerializableSingleton.getInstance();
		SerializableSingleton other = SerializableSingleton.getInstance();
		System.out.println(s.equals(other));
		SerializableSingleton copy = (SerializableSingleton)CopyUtils.deepCopy(s);
		System.out.println(s.equals(copy));
		
	}

}
