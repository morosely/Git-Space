package fills.tools.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author ysf
 * @Description   文件工具 //TODO
 * @Param
 * @Date 2020/11/30 14:18
 * @return
 **/
public class FileUtil {
	
	public static String filePath = "D:/fills-data/image2Text/tessData";

    /**
     * @Author ysf
     * @Description   根据文件路径获取文件 //TODO
     * @Param  [filePath]
     * @Date 2020/11/30 14:19
     * @return java.io.File
     **/
    public static File getFile(String filePath){
        return new File(filePath);
    }

    /**
     * @Author ysf
     * @Description   创建文件目录 //TODO
     * @Param  [filePath]
     * @Date 2020/11/30 14:24
     * @return void
     **/
    public static void createFilePath(String filePath){
        File file = new File(filePath);
        if(!file.exists()) {
            if(!filePath.endsWith(File.separator)){
                file.mkdirs();
            }

        }
    }

    /**
     * @Author ysf
     * @Description   创建文件 //TODO
     * @Param  [filePath]
     * @Date 2020/11/30 14:37
     * @return void
     **/
    public static boolean createFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            createFilePath(filePath.substring(0,filePath.lastIndexOf("/")));
            try {
                file.createNewFile();
                return true;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
    /**
     * @Author ysf
     * @Description   文件转byte //TODO
     * @Param  [filePath]
     * @Date 2020/12/17 10:24
     * @return byte[]
     **/
    public static byte[] file2Byte(String filePath){
        return file2Byte(new File(filePath));
    }

    /**
     * @Author ysf
     * @Description   文件转byte //TODO
     * @Param  [file]
     * @Date 2020/11/30 14:48
     * @return byte[]
     **/
    public static byte[] file2Byte(File file){
        byte[] bytes =new byte[1024];
        FileInputStream input = null;
        ByteArrayOutputStream output =null;
        byte[] data = null;
        try {
            input = new FileInputStream(file);
            output = new ByteArrayOutputStream();
            int numBytesRead = 0;
            while ((numBytesRead = input.read(bytes)) != -1) {
                output.write(bytes, 0, numBytesRead);
            }
            data = output.toByteArray();
        }catch (FileNotFoundException ex1) {
            System.out.println(ex1.getMessage());
        }catch (IOException ex1) {
            System.out.println(ex1.getMessage());
        }finally {
            try {
                output.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                input.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return data;
    }
    /**
     * @Author ysf
     * @Description   获取文件内容 By filePath //TODO
     * @Param  [filePath]
     * @Date 2020/12/19 9:47
     * @return java.lang.String
     **/
    public static String fileContentByFilePath(String filePath){
        return  fileContentByFile(new File(filePath));
    }

    /**
     * @Author ysf
     * @Description   获取文件内容 By file //TODO
     * @Param  [file]
     * @Date 2020/12/17 10:31
     * @return java.lang.String
     **/
    public static String fileContentByFile(File file)  {
        StringBuffer stringBuilder = new StringBuffer();
        FileReader fileReader =null;
    	BufferedReader bufferedReader =null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp + "\n\r");
                temp =null;
            }
            bufferedReader.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
        	try {
				if(fileReader!=null){
					fileReader.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        	try {
				if(bufferedReader!=null){
					bufferedReader.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        }
       return  stringBuilder.toString();
    }
    
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 读取文件内容
     *
     * @param: filePath
     * @param:@return
     * @return：List<String>
     *
     * @author: ysf
     * @date: 2021年4月16日 下午3:55:10 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月16日      ysf         读取文件内容
     */
    public static List<String> fileContentListByFilePath(String filePath){
    	
    	return fileContentListByFile(new File(filePath));
    }
    
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 读取文件内容
     *
     * @param: file
     * @param:@return
     * @return：List<String>
     *
     * @author: ysf
     * @date: 2021年4月16日 下午3:55:10 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月16日      ysf         读取文件内容
     */
    public static List<String> fileContentListByFile(File file){
    	List<String> contents = new ArrayList<String>();
    	FileInputStream fileReader =null;
    	BufferedReader bufferedReader =null;
    	try {
    		String encoding = getCode(file);
    		fileReader = new FileInputStream(file);
    		InputStreamReader read = new InputStreamReader (fileReader,encoding);
    		bufferedReader  = new BufferedReader(read);
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null) {
            	contents.add(temp);
            	temp =null;
            }
            bufferedReader.close();
        }catch (Exception e){
             System.out.println(e.getMessage());
        }finally{
        	try {
				if(fileReader!=null){
					fileReader.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        	try {
				if(bufferedReader!=null){
					bufferedReader.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        }
    	return contents;
    }

    /**
     * 
     * @Function: FileUtil.java
     * @Description: 确认文件编码
     *
     * @param: path
     * @param:@return
     * @param:@throws Exception
     * @return：String
     *
     * @author: ysf
     * @date: 2021年4月16日 下午3:56:04 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月16日      ysf         确认文件编码
     */
    public static String getCode(String path){
        return getCode(new File(path));
    }
    
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 确认文件编码
     *
     * @param: file
     * @param:@return
     * @param:@throws Exception
     * @return：String
     *
     * @author: ysf
     * @date: 2021年4月16日 下午3:56:04 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月16日      ysf         确认文件编码
     */
     public static String getCode(File file){
    	InputStream inputStream=null;
    	String code = "gb2312";  //或GBK
    	try {
    		inputStream = new FileInputStream(file);
            byte[] head = new byte[3];
            inputStream.read(head);
            if (head[0] == -1 && head[1] == -2 ){
                code = "UTF-16";
            }else if (head[0] == -2 && head[1] == -1 ){
                code = "Unicode";
            }else if(head[0]==-17 && head[1]==-69 && head[2] ==-65){
                code = "UTF-8";
            }
            inputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
         
        return code;
    }
    
    
    /**
     * @Author ysf
     * @Description   检验文件内容 //TODO
     * @Param  [file,macth]
     * @Date 2020/12/17 10:31
     * @return java.lang.String
     **/
    public static boolean checkFileContentByFile(File file,String macth)  {
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null) {
                if(temp.contains(macth)){
                    return true;
                }
                temp =null;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                bufferedReader.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                fileReader.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return  false;
    }
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 写入内容
     *
     * @param: content
     * @param: path
     * @param:@return
     * @return：boolean
     *
     * @author: ysf
     * @date: 2021年3月15日 下午5:12:09 
     */
    public static boolean writeContent(String content,String path){
    	try {
    		createFile(path);
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(content);
            out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
        }
    	
    	return true;
    }
    
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 删除文件
     *
     * @param: filePath
     * @param:@return
     * @return：boolean
     *
     * @author: ysf
     * @date: 2021年3月15日 下午5:13:10 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月15日      ysf         删除文件
     */
    public static boolean delFile(String filePath){
    	File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
    	return true;
    }
    
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 获取当前系统路径
     *
     * @param:@return
     * @return：String
     *
     * @author: ysf
     * @date: 2021年3月30日 下午8:10:16 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月30日      ysf         修改原因
     */
    public static String getCurrentPath(){
    	File file = new File(".");
    	return file.getAbsolutePath();
    }
    
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 查找文件、文件夹路径
     *
     * @param: file
     * @param:@return
     * @return：String
     *
     * @author: ysf
     * @date: 2021年4月2日 下午4:56:05 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月2日      ysf          查找文件、文件夹路径
     */
    public static String getCurrentOsPath(String file){
    	String path = Thread.currentThread().getContextClassLoader().getResource(file).getPath();
    	if(path.startsWith("/")){
    		path = path.substring(1, path.length());
    	}
    	return path;
    }
    
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 获取文件内容返回inputStream
     *
     * @param: fileName
     * @param:@return
     * @return：InputStream
     *
     * @author: ysf
     * @date: 2021年4月2日 下午5:00:18 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月2日      ysf          获取文件内容返回inputStream
     */
    public static InputStream getFileInputStream(String fileName){
    	return FileUtil.class.getClassLoader().getResourceAsStream(fileName);
    }
    
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 将InputStream写入本地文件
     *
     * @param: destination
     * @param: input
     * @param:@throws IOException
     * @return：void
     *
     * @author: ysf
     * @date: 2021年4月2日 下午4:56:56 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月2日      ysf          将InputStream写入本地文件
     */
    public static void writeToLocal(String localPath, InputStream input)
            throws IOException {
    	FileOutputStream downloadFile=null;
    	try {
    		int index;
            byte[] bytes = new byte[1024];
            downloadFile = new FileOutputStream(localPath);
            while ((index = input.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(downloadFile!=null){
				downloadFile.close();
			}
		}
    }
    
    /**
     * 
     * @Function: FileUtil.java
     * @Description: 查找文件并保存本地
     *
     * @param: localPath
     * @param: searchFile
     * @return：void
     *
     * @author: ysf
     * @date: 2021年4月2日 下午5:04:55 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月2日      ysf          查找文件并保存本地
     */
    public static void searchAndSaveLocal(String localPath,String searchFile){
    	if(!createFile(localPath)){
    		return;
    	}
    	String searchPath =getCurrentOsPath(searchFile);
    	if(searchPath!=null){
    		InputStream in = getFileInputStream(searchFile);
    		if(in!=null){
    			try {
    				writeToLocal(localPath, in);
				} catch (Exception e) {
					// TODO: handle exception
				}
    			try {
					in.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
    			
    		}
    	}
    }
    
    /**
     * 
     * @Function: Image2TextMain.java
     * @Description: 获取文库文件
     *
     * @param:
     * @return：void
     *
     * @author: ysf
     * @date: 2021年4月2日 下午5:20:42 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月2日      ysf          获取文库文件
     */
    public static void doActionFile(){
    	FileUtil.searchAndSaveLocal(filePath+"/chi_sim.traineddata", "tessData/chi_sim.traineddata");
    }
    
    
    public static void main(String[] args) {
    	/*System.out.println(FileUtil.class.getClassLoader().getResourceAsStream("tessData/chi_sim.traineddata"));
    	System.out.println(getCurrentPath());
    	System.out.println(getCurrentOsPath("tessData"));
    	System.out.println(getCurrentOsPath("tessData/chi_sim.traineddata"));
    	System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("tessData/chi_sim.traineddata"));*/
    	doActionFile();
    	
	}
}
