package fills.thread;



import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;

import javax.imageio.ImageIO;

import fills.tools.file.FilePdfUtil;
import fills.tools.file.FilePptUtil;
import fills.tools.file.FileUtil;
import fills.tools.file.FileWordUtil;
import fills.tools.file.FileXlsUtil;
import fills.tools.image.ImageToText;
import fills.tools.image.ImageUtil;

/**
 * @Author ysf
 * @Description   线程查找文件工具 //TODO
 * @Param
 * @Date 2020/12/15 20:09
 * @return
 **/
public class FileSearchThreadUtil extends Thread{

    //文件
    public File file;
    //匹配关键字
    private String macth;
    //命中内容
    private  Set<String> setKey;
    //查找类型 文件名、文件内容
    private String actionType;
   
    //文件名后缀
    private String suffix;
    
    
    public FileSearchThreadUtil(File file, String macth, Set<String> setKey,String actionType,String suffix) {
        this.file = file;
        this.macth = macth;
        this.setKey = setKey;
        this.actionType = actionType;
        this.suffix = suffix;
    }


    @Override
    public void run() {
    	try{
	        if(file!=null){
	            String filePath = file.getPath();
	            if("文件名".equals(actionType)&&checkFile(filePath)){
                    setKey.add(filePath);
                }else if("文件内容".equals(actionType) && file.isFile()){
                	if(checkFileContent(filePath)){
                		setKey.add(filePath);
                	}
                }
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println(e.getMessage());
    	}finally {
        }
    }

    /**
     * 校验文件名
     * @param filePath
     * @return
     */
    public boolean checkFile(String filePath) {
        String fileStr = filePath.substring(filePath.lastIndexOf("\\")+1,filePath.length());
        if(fileStr.contains(macth)){
            return true;
        }
        return false;
    }

    /**
     * 校验文件内容
     * @param filePath
     * @return
     * @throws Exception
     */
    public  boolean checkFileContent(String filePath) throws Exception{
        if("JPG,JPEG,PNG,GIF,jpg,jpeg,png,gif".contains(suffix)){
        	return checkFileImageByFile();
        }else if("log,txt,java,js,jsp,html,bat,cmd,css,sh,ini,properties,xml,php,py,dat,bin,hex,cpp,hpp,prj".contains(suffix)){
            return FileUtil.checkFileContentByFile(file,macth);
        }else if("doc,DOC".contains(suffix)){
            return checkFileDocByFile();
        }else if("docx,DOCX".contains(suffix)){
            return checkFileDocxByFile();
        }else if("xls,xlsx".contains(suffix)){
            return FileXlsUtil.checkXlsContentByFile(file,macth);
        }else if("ppt,PPT".contains(suffix)){
            return FilePptUtil.checkPptContentByFile(file,macth);
        }else if("pptx,PPTX".contains(suffix)){
            return FilePptUtil.checkPptxContentByFile(file,macth);
        }else if("pdf,PDF".contains(suffix)){
            return checkFilePdfByFile();
        }else {
            //return FileUtil.checkFileContentByFile(file,macth);
        	//其他文件不做处理
        	return false;
        }
    }

    /**
     * @Author ysf
     * @Description   校验图片内容 //TODO
     * @Param  
     * @Date 2020/12/19 11:13
     * @return boolean
     **/
    public boolean checkFileImageByFile(){
        if(file!=null) {
        	try {
				BufferedImage bi = ImageUtil.resizeTmage(ImageIO.read(file), 2.0);
				String content = ImageToText.getTextByBufferedImage(bi, "chi_sim", FileUtil.filePath);
	            if (content != null && content.contains(macth)) {
	                return true;
	            }
			} catch (Exception e) {
			}
            
        }
        return false;
    }
    
    /**
     * @Author ysf
     * @Description   校验DOC文档内容 //TODO
     * @Param  []
     * @Date 2020/12/19 11:18
     * @return boolean
     **/
    public boolean checkFileDocByFile(){
       String content = FileWordUtil.getDocContentByFile(file);
       if(content!=null&&content.contains(macth)){
           return true;
       }
       return false;
    }

    /**
     * @Author ysf
     * @Description   校验DOCX文档内容 //TODO
     * @Param  []
     * @Date 2020/12/19 11:18
     * @return boolean
     **/
    public boolean checkFileDocxByFile(){
        String content = FileWordUtil.getDocxContentByFile(file);
        if(content!=null&&content.contains(macth)){
            return true;
        }
        return false;
    }

    /**
     * @Author ysf
     * @Description   校验pdf文档内容 //TODO
     * @Param  []
     * @Date 2020/12/19 14:06
     * @return boolean
     **/
    public boolean checkFilePdfByFile(){
        String content = FilePdfUtil.getPdfContentByFile(file);
        if(content!=null&&content.contains(macth)){
            return true;
        }
        return false;
    }
}
