package com.datastructure;

import java.util.Arrays;

public class BubbleSort {

	public static void main(String[] args) {
		int arr[] = { 13, 10, 7, 82, 19, 50, 2, 32, 4, 9 };
		bubbleSort(arr);
		System.out.println("<==========Sort Finished==========>");
		System.out.println(Arrays.toString(arr));
	}

	// 冒泡排序
	public static void bubbleSort(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[i]) {
					swap(arr,j,i);
				}
			}
		}
	}

	// 交换
	public static void swap(int[] arr, int i, int j) {
		arr[i] ^= arr[j];
		arr[j] ^= arr[i];
		arr[i] ^= arr[j];
	}
}
