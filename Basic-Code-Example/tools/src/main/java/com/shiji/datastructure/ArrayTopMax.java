package com.shiji.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * 查找出数组中最大值的位置
 * @author Administrator
 *
 */
public class ArrayTopMax {

	public static int findMaxValue(int[] array){
		int[] newArray = Arrays.copyOf(array,array.length);
		Arrays.sort(newArray);
		return newArray[newArray.length-1];
	}
	
	public static int[] findTopNValue(int[] array){
		int max = findMaxValue(array);
		int[] result = new int[array.length];
		int j = 0;
		for (int i = 0; i < array.length; i++) {
			if(max == array[i]){
				result[j++] = array[i];
			}
		}
		return Arrays.copyOf(result,j);
	}
	
	public static void main(String[] args) {
		int[] array = new int[] {3,4,1,2,10,9,2,10,8,9,10,10,1,2,4,3 };
		int[] topNValue =  findTopNValue(array);
		System.out.println(Arrays.toString(topNValue)); 
	}
}
