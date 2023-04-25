package com.shiji.string;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.lang.String;

public class MyString implements IString{
	
	public static void main(String[] args) {
		/*List<String> handler = new ArrayList<String>();
		
		for(int i=0; i<1000; i++){
			//HugeString h = new HugeString();
			ImporvedHugeString h = new ImporvedHugeString();
			System.out.println(handler.add(h.getSubString(1, 5)));
		}*/

		/*IString proxyStr = (IString)MyProxyFactory.getProxy(new MyString());
		proxyStr.split();
		proxyStr.stringTokenizer();
		proxyStr.indexOfAndSubstring();
		proxyStr.addString();
		proxyStr.addString2();
		proxyStr.addStringBuffer();*/
		//Map map = new HashMap();
		Map map = new LinkedHashMap();
		map.put("1", "A");
		map.put("2", "B");
		map.put("3", "C");
		map.put("4", "D");
		map.get("3");
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			System.out.println(key + " ----- "+map.get(key));
			
		}
	}
	
	static class HugeString{
		private String str = new String(new char[1000000]);
		public String getSubString(int beginIndex,int endIndex){
			return str.substring(beginIndex, endIndex);
		}
	}
	
	static class ImporvedHugeString{
		private String str = new String(new char[100000]);
		public String getSubString(int beginIndex,int endIndex){
			return new String(str.substring(beginIndex, endIndex));
		}
	}
	
	
	public MyString(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 1000; i++) {
			sb.append(i);
			sb.append(";");
		}
		orgStr = sb.toString();
	}
	
	String orgStr = null;
	
	//1.split
	@Override
	public void split(){
		for (int i = 0; i < 10000; i++) {
			orgStr.split(";");
		}
	}
	
	//2.StringTokenizer
	@Override
	public void stringTokenizer(){
		StringTokenizer st = new StringTokenizer(orgStr,";");
		for (int i = 0; i < 10000; i++) {
			while(st.hasMoreTokens()){
				String s = st.nextToken();
			}
			st = new StringTokenizer(orgStr,";");
		}
	}
	
	//3.indexOf.substring()
	@Override
	public void indexOfAndSubstring(){
		String temp = orgStr;
		for (int i = 0; i < 10000; i++) {
			while(true){
				String splitStr = null;
				int j = temp.indexOf(';');
				if(j<0) break;
				splitStr = temp.substring(0,j);
				temp = temp.substring(j+1);
			}
		}
		temp = orgStr;
	}

	@Override
	public void addString() {
		for(int i=0; i <1000000 ;i++){
			String result = "a" + "b" +"c";
		}
		
	}

	@Override
	public void addStringBuffer() {
		for(int i=0; i <1000000 ;i++){
			StringBuffer sb = new StringBuffer();
			sb.append("a").append("b").append("c");
		}
	}

	@Override
	public void addString2() {
		for(int i=0; i <1000000 ;i++){
			String a = "a";
			String b = "b";
			String c = "c";
			String result = a + b + c;
		}
	}

	
}
