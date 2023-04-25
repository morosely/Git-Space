package com.shiji.key;

import java.util.HashMap;
import java.util.Map;

import javax.swing.text.Keymap;

public class KeyGenerator5 {
	private KeyInformation keyInformation;
	private static final int POOLSIZE = 20;
	private static Map<String,KeyGenerator5> keysMap = new HashMap<String,KeyGenerator5>(10);
	
	private KeyGenerator5(){
		
	}
	
	private KeyGenerator5(String keyName){
		keyInformation = new KeyInformation(POOLSIZE,keyName);
	}
	
	synchronized static public KeyGenerator5 getInstance(String keyName){

		/*if(keysMap.containsKey(keyName)){
			return keysMap.get(keyName);
		}else{
			keysMap.put(keyName, new KeyGenerator4(keyName));
			return keysMap.get(keyName);
		}*/
		if(!keysMap.containsKey(keyName)){
			keysMap.put(keyName, new KeyGenerator5(keyName));
		}
		return keysMap.get(keyName);
	}
	
	synchronized public int getKeyNext(){
		return keyInformation.getKeyNext();
	}
}