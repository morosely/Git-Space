package com.shiji.datastructure;

/**
 * 二分查找的前提是：已经排好序的数组
 *
 */
public class BinarySearch {

	public static void main(String[] args) {
		int[] array={1,3,5,7,9,10,12,15,20,30,34,45,56,57,59,70};
		System.out.println(binSearch(array,0,array.length-1,10));
	}

	 // 二分查找递归实现   
    public static int binSearch(int array[], int start, int end, int searchValue) {   
        int mid = (end - start) / 2 + start;   
        if (array[mid] == searchValue) {   
            return mid;   
        }else if (searchValue > array[mid]) {   
            return binSearch(array, mid + 1, end, searchValue);   
        }else if (searchValue < array[mid]) {   
            return binSearch(array, start, mid - 1, searchValue);   
        }   
        return -1;   
    } 
}
