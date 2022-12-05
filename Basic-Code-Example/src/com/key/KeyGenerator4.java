package com.key;

import java.util.HashMap;
import java.util.Map;

public class KeyGenerator4 {
	private static KeyGenerator4 keyGen = new KeyGenerator4();
	private static final int POOLSIZE = 20;
	private static Map<String,KeyInformation> keysInfoMap = new HashMap<String,KeyInformation>(10);

	private KeyGenerator4(){

	}
	
	public KeyGenerator4 getInstance(){
		return keyGen;
	}
	
	synchronized public int getKeyNext(String keyName){
		/*KeyInformation keyInfo;
		if(keysInfoMap.containsKey(keyName)){
			keyInfo = keysInfoMap.get(keyName);
		}else{
			keyInfo = new KeyInformation(POOLSIZE, keyName);
			keysInfoMap.put(keyName, keyInfo);
		}
		return keyInfo.getKeyNext();*/
		if(!keysInfoMap.containsKey(keyName)){
			keysInfoMap.put(keyName, new KeyInformation(POOLSIZE,keyName));
		}
		return keysInfoMap.get(keyName).getKeyNext();
	}
}
