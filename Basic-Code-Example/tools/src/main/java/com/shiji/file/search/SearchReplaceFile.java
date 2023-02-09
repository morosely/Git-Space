package com.shiji.file.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchReplaceFile {

    private static String fileName = "D:\\WorkSpaces\\Product\\cloudplatform\\omdmain\\shiji-omdmain-service\\src\\main\\java\\com\\efuture\\omdmain\\component";
    //private static String fileName = "D:\\WorkSpace\\Product\\cloudplatform\\omdmain\\shiji-omdmain-service\\src\\main\\java\\com\\efuture\\omdmain\\component";
    private static String fileEnd = "java";// 文件名称后缀 txt sql bat
    private static String searchStr = "查询异常";//要查找的字符串
    private static String replaceStr = "return ErrorMsg.buildFailure(session,\"1001059\");";//要查找的字符串
    private static Boolean ingronCase = true;// 是否区分大小写
    private static Boolean onOrOffReplaceFile = false;// 是否开启替换功能
    private static List<String>  filePathList = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        //replaceFile(searchStr,replaceStr);
        checkChinaChar();
    }

    public static void replaceFile(String oldStr,String newStr) throws IOException {
        filePathList = getFileList(fileName);
        for(int k = 0 ; k < filePathList.size(); k++) {
            BufferedReader reader = null;
            BufferedWriter writer = null;
            StringBuilder sb = new StringBuilder();// 使用StringBuilder对象保存文件内容
            File file = new File(filePathList.get(k));
            boolean needReplace = false;
            if (file.exists()) {
                String filePath = file.toString();
                int lineNum = 0;//行号
                try {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
                    ArrayList<String> fileContents = new ArrayList<String>();
                    String lineText = null;
                    while ((lineText = reader.readLine()) != null) {
                        ++lineNum;
                        if (ingronCase) {
                            if (lineText.contains(oldStr)) {
                                if(!lineText.trim().startsWith("//") && !lineText.trim().startsWith("*") && !lineText.trim().startsWith("logger") && !lineText.trim().startsWith("log")
                                        && !lineText.trim().startsWith("this.logger") && !lineText.trim().startsWith("this.log")){
                                    System.out.println("在【" + filePath +":"+ lineNum + "】文件中找到了:【" + oldStr + "】:\n内容："+lineText);
                                    //lineText = lineText.replace(oldStr,newStr);
                                    lineText = lineText.replace(lineText,"//"+lineText+"\n"+newStr);
                                    needReplace = true;
                                }
                                //break;
                            }
                        } else {
                            if (lineText.toLowerCase().contains(oldStr.toLowerCase())) {
                                if(!lineText.trim().startsWith("//") && !lineText.trim().startsWith("*") && !lineText.trim().startsWith("logger") && !lineText.trim().startsWith("log")
                                        && !lineText.trim().startsWith("this.logger") && !lineText.trim().startsWith("this.log")){
                                    System.out.println("在【" + filePath +":"+ lineNum + "】文件中找到了:【" + oldStr + "】:\n内容："+lineText);
                                    //lineText = lineText.replace(oldStr,newStr);
                                    lineText = lineText.replace(lineText,"//"+lineText+"\n"+newStr);
                                    needReplace = true;
                                }
                                //break;
                            }
                        }
                        fileContents.add(lineText);//将数据存入集合
                    }
                    //只有查到替换的文本才能写
                    if(onOrOffReplaceFile && needReplace) {
                        writer = new BufferedWriter(new FileWriter(file));
                        for (String string : fileContents) {
                            writer.write(string);//一行一行写入数据
                            writer.newLine();//换行
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    //查询中文
    public static void checkChinaChar() throws IOException {
        filePathList = getFileList(fileName);
        for(int k = 0 ; k < filePathList.size(); k++) {
            BufferedReader reader = null;
            File file = new File(filePathList.get(k));
            boolean needReplace = false;
            if (file.exists()) {
                String filePath = file.toString();
                int lineNum = 0;//行号
                try {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
                    ArrayList<String> fileContents = new ArrayList<String>();
                    String lineText = null;
                    while ((lineText = reader.readLine()) != null) {
                        ++lineNum;
                        //排除代码开头//,*,loger,log,this.loggger,this.log开头包含中文
                        if(!lineText.trim().startsWith("//") && !lineText.trim().startsWith("*") && !lineText.trim().startsWith("logger") && !lineText.trim().startsWith("log")
                                && !lineText.trim().startsWith("this.logger") && !lineText.trim().startsWith("this.log")){
                            if(lineText.indexOf("//")!=-1){//排除代码后面带中文注释的：saleOrg.setParentName(parentName);// 增加父亲节点名称
                                String newlineText = lineText.substring(0,lineText.indexOf("//"));
                                if(isContainChinese(newlineText)){
                                    System.out.println("在【" + filePath +":"+ lineNum + "】文件中发现中文\n内容："+lineText);
                            }
                            }else if(isContainChinese(lineText)){
                                System.out.println("在【" + filePath +":"+ lineNum + "】文件中发现中文\n内容："+lineText);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        reader.close();
                    }
                }
            }
        }
    }

    //获取文件
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


    //检查汉字
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
