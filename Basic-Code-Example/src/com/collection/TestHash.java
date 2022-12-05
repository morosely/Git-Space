package com.collection;

public class TestHash {

	public static void main(String[] args) {
		System.out.println(hash(4));
		System.out.println(4 << 3);  //100 000
		System.out.println(-7 >>> 3);//1111=8+4+2+1=1111
	}
	
	private static int hash(int h) {
		h += (h << 15) ^ 0xffffcd7d;
		h ^= (h >>> 10);
		h += (h << 3);
		h ^= (h >>> 6);
		h += (h << 2) + (h << 14);
		return h ^ (h >>> 16);
		}

}
