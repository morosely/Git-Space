package fills.tools.file;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;

import fills.tools.image.ImageUtil;

/*
 * @Author ysf
 * @Description   读取word文档 //TODO
 * @Param
 * @Date 2020/12/17 14:21
 * @return 
 **/
public class FileWordUtil {
	public static void main(String[] args) {
		XWPFDocument xwpfDocument =null;
        try {
            xwpfDocument = new XWPFDocument(new FileInputStream("D:/docxTestImage1615620193633.docx"));
            delDocxContentByXWPFDocument(xwpfDocument, "Evaluation Warning: The document was created with Spire.Doc for JAVA.");
            createDoctx(xwpfDocument, "D:/docxTestSealDel.docx");
        }catch(Exception e){
        	
        }finally{
        	try {
				if(xwpfDocument!=null){
					xwpfDocument.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        }
		
		
	}
    
    /**
     * @Author ysf
     * @Description  获取DOC文档内容 By FilePath  //TODO
     * @Param  [filePath]
     * @Date 2020/12/17 16:14
     * @return java.lang.String
     **/
    public static String getDocContentByFilePath(String filePath){
        return getDocContentByFile(new File(filePath));
    }

    /*
     * @Author ysf
     * @Description   获取DOC文档内容 By File //TODO
     * @Param  [file]
     * @Date 2020/12/17 15:08
     * @return java.lang.String
     **/
    public static String getDocContentByFile(File file){
        String content =null;
        try{
            content = getDocCOntentByInputStream(new FileInputStream(file));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return content;
    }

    /*
     * @Author ysf
     * @Description   获取DOC文档内容 By InputStream //TODO
     * @Param  [in]
     * @Date 2020/12/17 16:38
     * @return java.lang.String
     **/
    public static String getDocCOntentByInputStream(InputStream in){
        String content =null;
        WordExtractor extractor=null;
        try{
            extractor = new WordExtractor(in);
            content = extractor.getText();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(in!=null) {
                    in.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                if(extractor!=null) {
                    extractor.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
        return content;
    }
    
    /*
     * @Author ysf
     * @Description   获取DOCX文档内容 By filePath //TODO
     * @Param  [filePath]
     * @Date 2020/12/17 16:40
     * @return java.lang.String
     **/
    public static String getDocxContentByFilePath(String filePath){
        return getDocxContentByFile(new File(filePath));
    }


    /**
     * @Author ysf
     * @Description   获取DOCX文档内容 By file //TODO
     * @Param  [file]
     * @Date 2020/12/17 15:18
     * @return java.lang.String
     **/
    public static String getDocxContentByFile(File file){
        String content =null;
        try {
            content = getDocxContentByIntputStream(new FileInputStream(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return content;
    }

    /*
     * @Author ysf
     * @Description   获取DOCX文档内容 By inputStream //TODO
     * @Param  [in]
     * @Date 2020/12/17 16:41
     * @return java.lang.String
     **/
    public static String getDocxContentByIntputStream(InputStream in){
        String content =null;
        POIXMLTextExtractor extractor = null;
        XWPFDocument xwpfDocument =null;
        try {
            xwpfDocument = new XWPFDocument(in);
            extractor = new XWPFWordExtractor(xwpfDocument);
            content = extractor.getText();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                if(extractor!=null) {
                    extractor.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                if(xwpfDocument!=null) {
                    xwpfDocument.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {
                if(in!=null) {
                    in.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
        return content;
    }
    
    /**
     * 
     * @Function: FileWordUtil.java
     * @Description: docx替换内容
     *
     * @param: xwpfDocument
     * @param: oldContent
     * @param: newContent
     * @param:@return
     * @return：XWPFDocument
     *
     * @author: ysf
     * @date: 2021年3月13日 下午4:34:35 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月13日      ysf         docx替换内容
     */
    public static void updateDocxContentByXWPFDocument(XWPFDocument xwpfDocument,String oldContent,String newContent){
        try {
            // 读取文本内容
            for(XWPFParagraph para : xwpfDocument.getParagraphs()){
            	 List<XWPFRun> runs = para.getRuns();
            	 for(int i= 0;i<runs.size();i++){
            		 XWPFRun run = runs.get(i);
            		 String content = run.toString();
            		 if(content.contains(oldContent)||content.equals(oldContent)){
            			 para.removeRun(i);
            			 content = content.replaceAll(oldContent, newContent);
            			 para.insertNewRun(i).setText(content);
            		 }
            	 }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            
        }
    }
    
    /**
     * 
     * @Function: FileWordUtil.java
     * @Description: 插入图片/签章
     *
     * @param: xwpfDocument
     * @param: oldContent
     * @param: pictureData
     * @param:@return
     * @return：XWPFDocument
     *
     * @author: ysf
     * @date: 2021年3月15日 下午5:40:07 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月15日      ysf         插入图片/签章
     */
    public static void insertImage2XWPFDocument(XWPFDocument xwpfDocument,String oldContent,InputStream pictureData){
        try {
            // 读取文本内容
        	int j=0;
            for(XWPFParagraph para : xwpfDocument.getParagraphs()){
            	 List<XWPFRun> runs = para.getRuns();
            	 for(int i= 0;i<runs.size();i++){
            		 j++;
            		 XWPFRun run = runs.get(i);
            		 String content = run.toString();
            		 if(content.contains(oldContent)||content.equals(oldContent)){
            			 run.addPicture(pictureData, Document.PICTURE_TYPE_PNG, "seal"+j, Units.toEMU(160), Units.toEMU(160));
            		 }
            	 }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * 
     * @Function: FileWordUtil.java
     * @Description: docx 删除数据
     *
     * @param: xwpfDocument
     * @param: oldContent
     * @param:@return
     * @return：XWPFDocument
     *
     * @author: ysf
     * @date: 2021年3月13日 下午4:33:48 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月13日      ysf         docx 删除数据
     */
    public static void delDocxContentByXWPFDocument(XWPFDocument xwpfDocument,String oldContent){
        try {
            // 读取文本内容
            for(XWPFParagraph para : xwpfDocument.getParagraphs()){
            	 List<XWPFRun> runs = para.getRuns();
            	 for(int i= 0;i<runs.size();i++){
            		 XWPFRun run = runs.get(i);
            		 String content = run.toString();
            		 if(content.equals(oldContent)){
            			 para.removeRun(i);
            		 }else if(content.contains(oldContent)){
            			 para.removeRun(i);
            			 content = content.replaceAll(oldContent, "");
            			 para.insertNewRun(i).setText(content);
            		 }
            	 }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            
        }
    }
    /**
     * 
     * @Function: FileWordUtil.java
     * @Description: poi docx签章
     *
     * @param: xwpfDocument
     * @param: sealContent
     * @param: image
     * @param: index
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月16日 上午10:58:30 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月16日      ysf         poi docx签章
     */
    public static void docsSeal(XWPFDocument xwpfDocument,String sealContent,BufferedImage image,int index){
    	try {
            // 读取文本内容
    		int insertIndex =0;
            for(XWPFParagraph para : xwpfDocument.getParagraphs()){
            	 List<XWPFRun> runs = para.getRuns();
            	 for(int i= 0;i<runs.size();i++){
            		 XWPFRun run = runs.get(i);
            		 String content = run.toString();
            		 if(content.equals(sealContent) || content.contains(sealContent)){
            			 if(index==-1){
            				 docxSeal(run, image, "sealImage_"+insertIndex); 
            			 }else if(insertIndex==(index-1)){
            				 docxSeal(run, image, "sealImage_"+insertIndex);
            				 return;
            			 }
            			 insertIndex++;
            		 }
            	 }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            
        }
    }
    
    /**
     * 
     * @Function: FileWordUtil.java
     * @Description: poi docx签章
     *
     * @param: run
     * @param: image
     * @param: imageName
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月16日 上午11:04:39 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月16日      ysf         poi docx签章
     */
    public static void docxSeal(XWPFRun run,BufferedImage image,String imageName){
    	InputStream input = null;
        try {
        	input = ImageUtil.bufferedImage2InputStream(image);
			run.addPicture(input, Document.PICTURE_TYPE_PNG, imageName, Units.toEMU(160), Units.toEMU(160));
	        CTDrawing drawing = run.getCTR().getDrawingArray(0);
	        CTGraphicalObject graphicalobject = drawing.getInlineArray(0).getGraphic();
	        //拿到新插入的图片替换添加CTAnchor 设置浮动属性 删除inline属性
	        CTAnchor anchor = setImagePosition(graphicalobject, imageName,
	        		Units.toEMU(160), Units.toEMU(160),//图片大小
	                Units.toEMU(80), Units.toEMU(-70), true);//相对当前段落位置 需要计算段落已有内容的左偏移
	        drawing.setAnchorArray(new CTAnchor[]{anchor});//添加浮动属性
	        drawing.removeInline(0);//删除行内属性
		} catch (Exception e) {
		}finally{
			try {
				if(input!=null){
					input.close();
				}
			} catch (Exception e2) {
			}
		}
    }
    
    
    /**
     * @param ctGraphicalObject 图片数据
     * @param deskFileName      图片描述
     * @param width             宽
     * @param height            高
     * @param leftOffset        水平偏移 left
     * @param topOffset         垂直偏移 top
     * @param behind            文字上方，文字下方
     * @return
     * @throws Exception
     */
    public static CTAnchor setImagePosition(CTGraphicalObject ctGraphicalObject,
                                                String deskFileName, int width, int height,
                                                int leftOffset, int topOffset, boolean behind) {
        String anchorXML =
                "<wp:anchor xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
                        + "simplePos=\"0\" relativeHeight=\"0\" behindDoc=\"" + ((behind) ? 1 : 0) + "\" locked=\"0\" layoutInCell=\"1\" allowOverlap=\"1\">"
                        + "<wp:simplePos x=\"0\" y=\"0\"/>"
                        + "<wp:positionH relativeFrom=\"column\">"
                        + "<wp:posOffset>" + leftOffset + "</wp:posOffset>"
                        + "</wp:positionH>"
                        + "<wp:positionV relativeFrom=\"paragraph\">"
                        + "<wp:posOffset>" + topOffset + "</wp:posOffset>" +
                        "</wp:positionV>"
                        + "<wp:extent cx=\"" + width + "\" cy=\"" + height + "\"/>"
                        + "<wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/>"
                        + "<wp:wrapNone/>"
                        + "<wp:docPr id=\"1\" name=\"Drawing 0\" descr=\"" + deskFileName + "\"/><wp:cNvGraphicFramePr/>"
                        + "</wp:anchor>";
 
        CTDrawing drawing = null;
        try {
            drawing = CTDrawing.Factory.parse(anchorXML);
        } catch (Exception e) {
        }
        CTAnchor anchor = drawing.getAnchorArray(0);
        anchor.setGraphic(ctGraphicalObject);
        return anchor;
    }

    
    /**
     * 
     * @Function: FileWordUtil.java
     * @Description: 创建生成docx文档
     *
     * @param: xwpfDocument
     * @param: path
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月13日 下午4:34:12 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月13日      ysf         创建生成docx文档
     */
    public static void createDoctx(XWPFDocument xwpfDocument,String path){
    	OutputStream os =null;
    	try {
    		os = new FileOutputStream(path);  
        	xwpfDocument.write(os); 
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				if(os!=null){
					os.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {
				if(xwpfDocument!=null){
					xwpfDocument.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
    	
    }
 
    
}
