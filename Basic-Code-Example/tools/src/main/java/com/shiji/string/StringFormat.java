package com.shiji.string;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormat {

	public static void main(String[] args) {
		/*String s = "2009";
		System.out.println(String.format("%05d", 0)+0);
		System.out.println(replaceBlank("12345\r7890"));
		System.out.println("12345\r7890");*/
		String ss = "1234/456/7788789/abc";
		int index = ss.lastIndexOf("/");
		String indexStr1 = ss.substring(0,index);
		System.out.println(indexStr1);
		int index2 = indexStr1.lastIndexOf("/");
		String indexStr2 = indexStr1.substring(0,index2);
		System.out.println(indexStr2);
	}
	
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
