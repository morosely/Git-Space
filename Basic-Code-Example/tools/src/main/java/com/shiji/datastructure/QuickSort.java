package com.shiji.datastructure;

import java.util.Arrays;

public class QuickSort {

	public static void main(String[] args) {
		 int[] arr={1,3,2,45,65,33,12,50,23,45,68,90};
		 quickSort(arr,12,arr.length-1);
		 System.out.println(Arrays.toString(arr));
	}


	public static int partition(int []array,int lo,int hi){
        //固定的切分方式
        int key=array[lo];
        while(lo<hi){
            while(array[hi]>=key&&hi>lo){//从后半部分向前扫描
                hi--;
            }
            array[lo]=array[hi];
            while(array[lo]<=key&&hi>lo){//从前半部分向后扫描
                lo++;
            }
            array[hi]=array[lo];
        }
        array[hi]=key;
        return hi;
    }
    
    public static void quickSort(int[] array,int lo ,int hi){
        if(lo>=hi){
            return ;
        }
        int index=partition(array,lo,hi);
        quickSort(array,lo,index-1);
        quickSort(array,index+1,hi); 
    }
}
