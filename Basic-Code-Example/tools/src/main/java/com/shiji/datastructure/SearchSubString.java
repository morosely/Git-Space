package com.shiji.datastructure;

import java.util.Arrays;

/**
 * 连续数字串并且返回最长的数字字符串长度
 * 
 * @author Administrator
 * 
 */
public class SearchSubString {

	public static void main(String[] args) {
		//String str = "abc123bcd54321.agb345sabcbac123";
		String str = "abc123";
		String[] strArr = str.split("");
		System.out.println(Arrays.toString(strArr));
		System.out.println(str.length()+ " " + strArr.length);
		for (int i = 1; i < strArr.length; i++) {
			System.out.print(strArr[i]);
		}
//		System.out.println(getMaxNum(str));
	}

	public static StringBuffer getMaxNum(String str) {
		int maxLength = 0;
		StringBuffer maxNumBuffer = new StringBuffer();
		int nowLength = 0;
		StringBuffer nowNumBuffer = null;
		for (int i = 0; i < str.length(); i++) {
			char currentChar = str.charAt(i);
			// 判断该字符是不是数字
			if (currentChar >= 48 && currentChar <= 57) {
				nowNumBuffer = nowNumBuffer == null ? new StringBuffer() : nowNumBuffer;
				nowNumBuffer.append(currentChar);
				nowLength++;
				if (nowLength >= maxLength) {
					maxLength = nowLength;
					maxNumBuffer = nowNumBuffer;
				}
			} else {
				// 用于连续数字之后非数字，清楚当前的nowNumBuffer
				nowLength = 0;
				nowNumBuffer = null;
			}
		}
		return maxNumBuffer;
	}

}
