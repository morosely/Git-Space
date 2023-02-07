/*
package fills.tools.file;


import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.TextSelection;
import com.spire.doc.documents.TextWrappingStyle;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.fields.TextRange;

import fills.tools.image.ImageDrawSeal;
import fills.tools.image.ImageUtil;

*/
/**
 * 
 * @ClassName: FileDocUtil.java
 * @Description: docx签章工具
 * @author: ysf
 * @date: 2021年3月13日 下午4:52:07 
 *
 * Modification History:
 * Date         Author         Description
 * 2021年3月13日      ysf            docx签章工具
 *//*


public class FileSpireDocUtil {
	//spire.doc 多打印的行记录
	public static final String delContent ="Evaluation Warning: The document was created with Spire.Doc for JAVA.";
	
	
	*/
/**
	 * 
	 * @Function: FileDocUtil.java
	 * @Description: docx签章,spire.doc jar 生成docx会新增一行 “Evaluation Warning: The document was created with Spire.Doc for JAVA.”
	 *
	 * @param: content  签章位置
	 * @param: imageBytes 签章图片 byte[]
	 * @param: orgDocx  //docx path
	 * @param: newDocx  //docx path
	 * @param: index    //第几序列签章 -1 默认是所有位置签章
	 * @return：void
	 *
	 * @author: ysf
	 * @date: 2021年3月13日 下午4:49:34 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年3月13日      ysf         docx签章
	 *//*

	public static void docxSeal(String content,byte[]imageBytes,String orgDocx,String newDocx,int index){
		Document doc = new Document(orgDocx);
		TextSelection[] selections = doc.findAllString(content, true, true);
		if(selections==null){
			return;
		}
		if(index==-1){
			for(TextSelection selection:selections){
				docxSeal(doc, selection, imageBytes);
			}
		}else{
			if(selections.length<=index){
				docxSeal(doc, selections[index-1], imageBytes);
			}
		}
		//保存文档
		doc.saveToFile(newDocx, FileFormat.Docx_2013);
		doc.close();
	}
	
	*/
/**
	 * 
	 * @Function: FileDocUtil.java
	 * @Description: docx签章
	 *
	 * @param: doc
	 * @param: selection
	 * @param: imageBytes
	 * @return：void
	 *
	 * @author: ysf
	 * @date: 2021年3月13日 下午4:50:00 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年3月13日      ysf         docx签章
	 *//*

	private static void docxSeal(Document doc,TextSelection selection,byte[]imageBytes){
		DocPicture pic = new DocPicture(doc);
	    pic.setWidth(160);
	    pic.setHeight(160);
        pic.loadImage(imageBytes);
        pic.setTextWrappingStyle(TextWrappingStyle.Behind);
        TextRange range  = selection.getAsOneRange();
        int index = range.getOwnerParagraph().getChildObjects().indexOf(range);
        range.getOwnerParagraph().getChildObjects().insert(index,pic);
	}
	
	public static void main(String[] args) throws Exception{
		BufferedImage image =ImageDrawSeal.drawSeal(null, "菲尔斯有限公司", "12345678910", false, false,true);
		byte[] imageBytes = ImageUtil.bufferedImage2byte(image);
		FileSpireDocUtil.docxSeal("执行一个判定逻辑", imageBytes, "D:/Switch 用法解读.docx", "D:/Switch 用法解读temp.doc", -1);
		XWPFDocument xwpfDocument =new XWPFDocument(new FileInputStream("D:/Switch 用法解读temp.doc"));
		//删除 spire.doc 添加的行记录
		FileWordUtil.delDocxContentByXWPFDocument(xwpfDocument, FileSpireDocUtil.delContent);
		FileWordUtil.createDoctx(xwpfDocument, "D:/Switch 用法解读_seal.doc");
		FileUtil.delFile("D:/Switch 用法解读temp.doc");
	}

}
*/
