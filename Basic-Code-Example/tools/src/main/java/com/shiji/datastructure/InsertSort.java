package com.shiji.datastructure;

import java.util.Arrays;

public class InsertSort {

	public static void insertSort(int[] arr) {
		int temp = 0;
		// 进行len-1次循环，每次循环都将下标为i的元素插入到它前面已经排好序的队列中
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < arr[i - 1]) {
				temp = arr[i];
				while (i > 0 && temp < arr[i - 1]) {
					arr[i] = arr[i - 1];
					i--;
				}
				arr[i] = temp;
			}
		}
		
	}

	public static void main(String[] args) {
		int[] arr = new int[] { 5, 2, 0, 6, 1, 3 };
		insertSort(arr);
		System.out.println("<==========Sort Finished==========>");
		System.out.println(Arrays.toString(arr));
	}
}
