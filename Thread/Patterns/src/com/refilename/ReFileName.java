package com.refilename;

import java.io.File;
import java.io.IOException;

public class ReFileName {
	
    public void getFileName(File file){
    	//判断是否是文件
        if(file.isFile()){
            System.out.println(file.getPath()+file.getName());
        }else{
        	//如果是目录,列出当前目录下所有目录
            File[] f=file.listFiles();
            for (int i = 0; i < f.length; i++) {
            	//判断是否是文件
                if(f[i].isFile()){
                    String oldFileName = f[i].getPath();
                    String newFileName = f[i].getPath().substring(0,(oldFileName.lastIndexOf('\\')))+"\\0"+f[i].getName();
                    new File(oldFileName).renameTo(new File(newFileName)); 
                }else{
                	//回调方法
                    this.getFileName(f[i]);
                }
            }
        }
    }
    

    public static void main(String[] args) throws IOException {
//        File pathFile = new File("D:\\Downloads\\03.回到恐龙时代");
//        ReFileName reFileName = new ReFileName();
//        reFileName.getFileName(pathFile);
        
    }
}
