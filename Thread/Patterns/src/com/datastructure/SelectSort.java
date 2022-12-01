package com.datastructure;
/**
 * 选择排序
 *
 */
public class SelectSort {

	public static void main(String[] args) {
        int[] arr={1,3,2,45,65,33,12};
        System.out.println("交换之前：");
        for(int num:arr){
            System.out.print(num+" ");
        }        
        //选择排序的优化
        for(int i = 0; i < arr.length - 1; i++) {// 做第i趟排序
            int temp = i;
            for(int j = temp + 1; j < arr.length; j++){// 选最小的记录
                if(arr[j] < arr[temp]){ 
                    temp = j; //记下目前找到的最小值所在的位置
                }
            }
            //在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换
            if(i != temp){  //交换a[i]和a[k]
            	swap(arr,i,temp);
             }    
        }
        System.out.println();
        System.out.println("交换后：");
        for(int num:arr){
            System.out.print(num+" ");
        }
    }
	
	// 交换
	private static void swap(int[] arr, int i, int j) {
		arr[i] ^= arr[j];
		arr[j] ^= arr[i];
		arr[i] ^= arr[j];
	}
}
