package com.shiji.datastructure;

/**
 * 二分查找的前提是：已经排好序的数组
 *
 */
public class BinarySearch {

    private static int loop = 0;
	public static void main(String[] args) {
		int[] array={3,5,7,9,10,12,15,20,30,34,45,56,57,59,70};
		System.out.println(binSearch(array,0,array.length-1,41));
	}

	 // 二分查找递归实现   
    public static int binSearch(int array[], int start, int end, int searchValue) {
        int mid = (end - start) / 2 + start;
        System.out.println(String.format("loop:%s, indexStart:%s, indexEnd:%s , indexMid:%s",++loop,start,end,mid));
        if(start > end) {//递归终结条件，并未找到key值
            return -1;
        }else if (searchValue > array[mid]) {
            return binSearch(array, mid + 1, end, searchValue);   
        }else if((searchValue < array[mid])){
            return binSearch(array, start, mid - 1, searchValue);   
        }else{
            return mid; //searchValue == array[mid]
        }
    } 
}
