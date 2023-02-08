package com.shiji.file.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SearchReplaceFile {

    //private static String fileName = "D:\\WorkSpaces\\Product\\cloudplatform\\omdmain\\shiji-omdmain-service\\src\\main\\java\\com\\efuture\\omdmain\\component";
    private static String fileName = "D:\\WorkSpace\\Product\\cloudplatform\\omdmain\\shiji-omdmain-service\\src\\main\\java\\com\\efuture\\omdmain\\component";

    private static String fileEnd = "java";// 文件名称后缀 txt sql bat

    private static String searchStr = "查询异常";//要查找的字符串
    private static String replaceStr = "查-询-异-常";//要查找的字符串

    private static Boolean ingronCase = true;// 是否区分大小写
    private static Boolean isReplaceFlag = false;// 是否开启替换功能
    private static List<String>  filePathList = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        int count = 0;
        filePathList = getFileList(fileName);
        for(int k = 0 ; k < filePathList.size(); k++) {
            BufferedReader reader = null;
            BufferedWriter writer = null;
            StringBuilder sb = new StringBuilder();// 使用StringBuilder对象保存文件内容
            File file = new File(filePathList.get(k));
            boolean hasReplaceFile = false;
            if (file.exists()) {
                String filePath = file.toString();
                count++;
                //System.out.println("正在读取第" + count + "个文件:" + s);
                /* 读取数据 */
                int lineNum = 0;//行号
                try {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
                    ArrayList<String> fileContents = new ArrayList<String>();
                    String lineText = null;
                    while ((lineText = reader.readLine()) != null) {
                        ++lineNum;
                        if (ingronCase) {
                            if (lineText.contains(searchStr) ) {
                                if(!"//".equals(lineText.trim().substring(0,2)) && !"*".equals(lineText.trim().substring(0,1))){
                                    System.out.println("在【" + filePath +":"+ lineNum + "】文件中找到了:【" + searchStr + "】:\n内容："+lineText);
                                    lineText = lineText.replace(searchStr, replaceStr);
                                    hasReplaceFile = true;
                                }

                                //break;
                            }
                        } else {
                            if (lineText.toLowerCase().contains(searchStr.toLowerCase())) {
                                if(!"//".equals(lineText.trim().substring(0,2)) && !"*".equals(lineText.trim().substring(0,1))){
                                    System.out.println("在【" + filePath +":"+ lineNum + "】文件中找到了:【" + searchStr + "】:\n内容："+lineText);
                                    lineText = lineText.replace(searchStr, replaceStr);
                                    hasReplaceFile = true;
                                }
                                //break;
                            }
                        }
                        fileContents.add(lineText);//将数据存入集合
                    }
                    //只有查到替换的文本才能写
                    if(isReplaceFlag && hasReplaceFile) {
                        writer = new BufferedWriter(new FileWriter(file));
                        for (String string : fileContents) {
                            writer.write(string);//一行一行写入数据
                            writer.newLine();//换行
                        }
                    }

                } catch (Exception e) {
                    System.err.println("读取文件错误 :" + e);
                }finally {
                    if(reader!=null){
                        reader.close();
                    }
                    if(writer!=null){
                        writer.close();
                    }
                }
            }
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
                    filePathList.add(strFileName);
                } else {
                    continue;
                }
            }
        }
        return filePathList;
    }

}
