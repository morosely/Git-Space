package com.shiji.file.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {

    private static String fileName = "D:\\WorkSpaces\\Product\\cloudplatform\\omdmain\\shiji-omdmain-service\\src\\main\\java\\com\\efuture\\omdmain\\component";

    private static String fileEnd = "java";// 文件名称后缀 txt sql bat

    private static String searchStr = "参数错误";//要查找的字符串

    private static Boolean ingronCase = true;// 是否区分大小写

    private static List<String> pathlist = new ArrayList<>();

    public static void main(String[] args) {
        List<String> resultList = new ArrayList<>();
        int count = 0;
        pathlist = getFileList(fileName);
        for(int k=0; k<pathlist.size(); k++) {
            File file = new File(pathlist.get(k));
            if (file.exists()) {
                String s = file.toString();
                count++;
                //System.out.println("正在读取第" + count + "个文件:" + s);
                /* 读取数据 */
                int lineNum = 0;//行号
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(s)), "UTF-8"));
                    String lineTxt = null;
                    while ((lineTxt = br.readLine()) != null) {
                        ++lineNum;
                        if (ingronCase) {
                            if (lineTxt.contains(searchStr)) {
                                resultList.add("在【" + s + "】文件中找到了:【" + searchStr+"】行号"+lineNum);
                                //break;
                            }
                        } else {
                            if (lineTxt.toLowerCase().contains(searchStr.toLowerCase())) {
                                resultList.add("在【" + s + "】文件中找到了:【" + searchStr+"】行号"+lineNum);
                                //break;
                            }
                        }
                    }
                    br.close();
                } catch (Exception e) {
                    System.err.println("读取文件错误 :" + e);
                }
            }
        }
        System.out.println("===============输出结果===============");
        // 输出结果
        for (int i = 0; i < resultList.size(); i++) {
            System.out.println(resultList.get(i));
        }

    }

    public static List<String> getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("."+ fileEnd)) { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getAbsolutePath();
                    pathlist.add(strFileName);
                } else {
                    continue;
                }
            }
        }
        return pathlist;
    }

}
