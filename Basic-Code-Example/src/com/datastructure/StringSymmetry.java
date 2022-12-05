package com.datastructure;

import java.util.Arrays;
/**
 *
 * 字符串是否对称 
 */

public class StringSymmetry {

	public static void main(String[] args) {
		String s = "abcgcba1";
		System.out.println(symmetry(s));
		
	}
	
	//第一种方法
	public static boolean symmetry(String s){
		if(s!=null && s.length()>1){
			StringBuffer sb = new StringBuffer(s);
			String reStr = sb.reverse().toString();
			if(reStr.equals(s))
				return true;
		}
		return false;
	}
	
    //第二种方法
	public static boolean symmetry2(String s){
		StringBuffer sb = new StringBuffer();
		for (int i=s.length(); i>0; i--) {
			sb.append((s.substring(i-1, i)));
		}
		return s.equals(sb.toString());
	}
}
