package com.shiji.autopolling;

public class ENV13 {

	public static String EAN13_AddBarcodeCheckbit(String dzcm)
	{
		int odd = 0;	// 奇数
		int even = 0;	// 偶数
		
		for (int i = 0; i < dzcm.length(); i++)
		{
			if (i % 2 == 0)
			{
				odd = odd + Integer.parseInt(dzcm.substring(i, i + 1));
			}
			else
			{ 
				even = even + Integer.parseInt(dzcm.substring(i, i + 1));
			}
		}
		
		int a = odd + even * 3;
		int checbit = (10 - (a % 10)) % 10;
		
		return dzcm + String.valueOf(checbit) ;
	}
}
