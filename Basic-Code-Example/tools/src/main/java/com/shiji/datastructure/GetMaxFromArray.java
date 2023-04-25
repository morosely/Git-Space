package com.shiji.datastructure;

public class GetMaxFromArray {
	
	 public static void main(String []args){
         int[] arr={3,54,456,342,2798};
         int max=getMax(arr);
         System.out.print("max="+max);//max=2798
     }
 
     /**
      * 取出数组中的最大值
      * @param arr
      * @return
      */
     public static int getMax(int[] arr){
         int max=arr[0];
         for(int i=1;i<arr.length;i++){
             if(arr[i]>max){
                 max=arr[i];
             }
         }
         return max;
     }
}
