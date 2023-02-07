package fills.tools.file;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/*
 * @Author ysf
 * @Description   pdf解析工具 //TODO
 * @Param
 * @Date 2020/12/18 20:33
 * @return
 **/
public class FilePdfUtil {

    /**
     * @Author ysf
     * @Description   解析pdf文件 By filePath //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:44
     * @return java.lang.String
     **/
    public static String getPdfContentByFilePath(String filePath){
        return getPdfContentByFile(new File(filePath));
    }

    /**
     * @Author ysf
     * @Description   解析pdf文件 By file //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:44
     * @return java.lang.String
     **/
    public static String getPdfContentByFile(File file){
        String content =null;
        try {
            content = getPdfContentByInputStream(new FileInputStream(file));
        }catch (Exception e){
            System.out.println(e.getMessage()+file.getPath());
        }
        return content;
    }

    /**
     * @Author ysf
     * @Description   解析pdf文件 By inputStream //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:44
     * @return java.lang.String
     **/
    public static String getPdfContentByInputStream(InputStream in){
        StringBuffer content = new StringBuffer();
        RandomAccessBuffer randomAccessBuffer=null;
        try {
            randomAccessBuffer = new RandomAccessBuffer(in);
            PDFParser parser = new PDFParser(randomAccessBuffer);
            parser.parse();
            // 读取文本内容
            PDDocument document = parser.getPDDocument();
            // 获取页码
            int pages = document.getNumberOfPages();
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pages);
            content.append(stripper.getText(document));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try{
                if(in!=null){
                    in.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try{
                if(randomAccessBuffer!=null){
                    randomAccessBuffer.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return content.toString();
    }
}
