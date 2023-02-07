package fills.tools.file;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.extractor.SlideShowExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @Author ysf
 * @Description   解析PPT工具 //TODO
 * @Param
 * @Date 2020/12/18 20:49
 * @return
 **/
public class FilePptUtil {
    /*
     * @Author ysf
     * @Description   解析ppt By filePath //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:58
     * @return java.lang.String
     **/
    public static String getPptContentByFilePath(String filePath){
        return getPptContentByFile(new File(filePath));
    }
    /*
     * @Author ysf
     * @Description   解析ppt By file //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:58
     * @return java.lang.String
     **/
    public static String getPptContentByFile(File file){
        String content =null;
        try {
            content = getPptContentByInputStream(new FileInputStream(file));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return content;
    }

    /*
     * @Author ysf
     * @Description   解析ppt By inputStream //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:58
     * @return java.lang.String
     **/
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String getPptContentByInputStream(InputStream in){
        StringBuffer content = new StringBuffer();
		SlideShowExtractor slideShowExtractor =null;
        try {
            HSLFSlideShow hslfSlideShow = new HSLFSlideShow(in);
            List<HSLFSlide> slides = hslfSlideShow.getSlides();
            slideShowExtractor = new SlideShowExtractor(hslfSlideShow);
            for (HSLFSlide slide : slides) {
                content.append(slideShowExtractor.getText(slide));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(in!=null){
                    in.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                if(slideShowExtractor!=null){
                    slideShowExtractor.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return content.toString();
    }

    /*
     * @Author ysf
     * @Description   解析pptx By filePath //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:58
     * @return java.lang.String
     **/
    public static String getPptxContentByFilePath(String filePath){
        return getPptxContentByFile(new File(filePath));
    }

    /*
     * @Author ysf
     * @Description   解析ppt By file //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:58
     * @return java.lang.String
     **/
    public static String getPptxContentByFile(File file){
        String content =null;
        try{
            content = getPptxContentByInputStream(new FileInputStream(file));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
         return content;
    }
    /*
     * @Author ysf
     * @Description   解析pptx By inputStream //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:58
     * @return java.lang.String
     **/
    public static String getPptxContentByInputStream(InputStream in){
        XMLSlideShow xmlSlideShow = null;
        StringBuffer content = new StringBuffer();
        try{
            xmlSlideShow = new XMLSlideShow(in);
            List<XSLFSlide> slides = xmlSlideShow.getSlides();            //获得每一张幻灯片
            for (XSLFSlide slide : slides) {
                CTSlide rawSlide = slide.getXmlObject();
                CTGroupShape spTree = rawSlide.getCSld().getSpTree();
                List<CTShape> spList = spTree.getSpList();
                for (CTShape shape : spList) {
                    CTTextBody txBody = shape.getTxBody();
                    if (null == txBody) {
                        continue;
                    }
                    List<CTTextParagraph> pList = txBody.getPList();
                    for (CTTextParagraph textParagraph : pList) {
                        List<CTRegularTextRun> textRuns = textParagraph.getRList();
                        for (CTRegularTextRun textRun : textRuns) {
                            content.append(textRun.getT()+"\n\r");
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(xmlSlideShow!=null){
                    xmlSlideShow.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                if(in!=null){
                    in.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return content.toString();
    }

    /**
     * @Author ysf
     * @Description   校验ppt文本内容 //TODO
     * @Param  [file]
     * @Date 2020/12/19 13:48
     * @return boolean
     **/
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static boolean checkPptContentByFile(File file,String macth){
        SlideShowExtractor slideShowExtractor =null;
        InputStream in =null;
        try {
            in = new FileInputStream(file);
            HSLFSlideShow hslfSlideShow = new HSLFSlideShow(in);
            List<HSLFSlide> slides = hslfSlideShow.getSlides();
            slideShowExtractor = new SlideShowExtractor(hslfSlideShow);
            for (HSLFSlide slide : slides) {
                if(slideShowExtractor.getText(slide).contains(macth)){
                    return true;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(slideShowExtractor!=null){
                    slideShowExtractor.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                if(in!=null){
                    in.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
    
    /**
     * @Author ysf
     * @Description   校验pptx文本内容 //TODO
     * @Param  [file,macth]
     * @Date 2020/12/19 13:48
     * @return boolean
     **/
    @SuppressWarnings("resource")
	public static boolean checkPptxContentByFile(File file,String macth){
        XMLSlideShow xmlSlideShow = null;
        InputStream in =null;
        try{
            in = new FileInputStream(file);
            xmlSlideShow = new XMLSlideShow(in);
            List<XSLFSlide> slides = xmlSlideShow.getSlides();            //获得每一张幻灯片
            for (XSLFSlide slide : slides) {
                CTSlide rawSlide = slide.getXmlObject();
                CTGroupShape spTree = rawSlide.getCSld().getSpTree();
                List<CTShape> spList = spTree.getSpList();
                for (CTShape shape : spList) {
                    CTTextBody txBody = shape.getTxBody();
                    if (null == txBody) {
                        continue;
                    }
                    List<CTTextParagraph> pList = txBody.getPList();
                    for (CTTextParagraph textParagraph : pList) {
                        List<CTRegularTextRun> textRuns = textParagraph.getRList();
                        for (CTRegularTextRun textRun : textRuns) {
                            if(textRun.getT().contains(macth)){
                                return true;
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage()+file.getPath());
        }finally {
            try {
                if(xmlSlideShow!=null){
                    xmlSlideShow.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                if(in!=null){
                    in.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
}
