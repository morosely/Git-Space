package fills.tools.image;


import java.awt.image.BufferedImage;
import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/*
 * @Author ysf
 * @Description   图片转Text工具 //TODO
 * @Param
 * @Date 2020/11/30 9:43
 * @return 
 **/
public class ImageToText {


    /**
     * @Author ysf
     * @Description   根据图片路径获取文字信息 //TODO
     * @Param  [filePath, language, languageDb]
     * @Date 2020/11/30 15:13
     * @return java.lang.String
     **/
    public static String getTextByImageFilePath(String imageUrl,String language,String languageDb){
        return getTextByITesseract( new File(imageUrl),language,languageDb);
    }


    /**
     * @Author ysf
     * @Description   根据图片文件获取文字信息 //TODO
     * @Param  [file, language, languageDb]
     * @Date 2020/11/30 15:09
     * @return java.lang.String
     **/
    public static String getTextByImageFile(File file,String language,String languageDb){
        return getTextByITesseract(file,language,languageDb);
    }

    /**
     * @Author ysf
     * @Description   根据图片Image 获取文字信息 //TODO
     * @Param  [bufferedImage, language, languageDb]
     * @Date 2020/11/30 15:08
     * @return java.lang.String
     **/
    public static String getTextByBufferedImage(BufferedImage bufferedImage,String language,String languageDb){
        return getTextByITesseract(bufferedImage,language,languageDb);
    }

    /**
     * @Author ysf
     * @Description   根据图片byte 获取文字信息 //TODO
     * @Param  [imageData, language, languageDb]
     * @Date 2020/11/30 15:07
     * @return java.lang.String
     **/
    public static String getTextByImageByte(byte[] imageData,String language,String languageDb){
        BufferedImage bufferedImage = ImageUtil.byte2BufferedImage(imageData);
        return getTextByITesseract(bufferedImage,language,languageDb);
    }


    public static String getTextByITesseract(Object obj,String language,String languageDb) {
        System.setProperty("jna.library.path", "32".equals(System.getProperty("sun.arch.data.model")) ? "win32-x86" : "win32-x86-64");
        //执行转换
        String result = null;
        try {
            ITesseract instance=  new Tesseract();
            //设置训练文件目录
            instance.setDatapath(languageDb);
            //设置训练语言
            instance.setLanguage(language);
            if (obj instanceof File) {
                //创建tess对象
                result = instance.doOCR((File) obj);
            }else if(obj instanceof BufferedImage){
                result = instance.doOCR((BufferedImage) obj);
            }

        } catch(TesseractException e){
            //System.out.println(e.getMessage());
        }
        return result;
    }
    
    public static void main(String[] args) {
    	System.out.println(getTextByImageFile(new File("D:/d.png"), "chi_sim", "D:/tessData_new/"));
	}
}
