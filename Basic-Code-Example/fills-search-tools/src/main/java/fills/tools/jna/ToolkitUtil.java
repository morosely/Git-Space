package fills.tools.jna;

import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;

import fills.tools.image.ImageUtil;
import fills.tools.md5.MD5Util;

/**
 * 
 * @ClassName: ToolkitUtil.java
 * @Description: 复制内容到剪切板
 * @author: ysf
 * @date: 2021年2月22日 下午5:46:25 
 *
 * Modification History:
 * Date         Author         Description
 * 2021年2月22日      ysf            修改原因
 */
public class ToolkitUtil {
	//校验去重集合
	public static Set<String> contents = new LinkedHashSet<String>();
	//获取有效数据集合
	public static List<Object> contentsList = new ArrayList<Object>();
	
	/**
	 * 
	 * @Function: ToolkitUtil.java
	 * @Description: java实现ctrl+c
	 *
	 * @param:
	 * @return：void
	 *
	 * @author: ysf
	 * @date: 2021年2月25日 下午4:55:42 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年2月25日      ysf         java实现ctrl+c
	 */
	public static void doCopy(){
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	/**
	 * 
	 * @Function: ToolkitUtil.java
	 * @Description: java实现ctrl+v
	 *
	 * @param:
	 * @return：void
	 *
	 * @author: ysf
	 * @date: 2021年2月25日 下午4:56:22 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年2月25日      ysf         java实现ctrl+v
	 */
	public static void doPaste(){
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	public static void doShowContent(){
		Thread doOutcontents = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try {
						for(Object obj: contentsList){
							System.out.println(contentsList.size()+"_"+obj);
						}
						Thread.sleep(5000);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
		doOutcontents.start();
	}
	/**
	 * 
	 * @Function: ToolkitUtil.java
	 * @Description: 从剪切板获取收集数据信息
	 *
	 * @param:
	 * @return：void
	 *
	 * @author: ysf
	 * @date: 2021年2月25日 下午4:56:44 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年2月25日      ysf         从剪切板获取收集数据信息
	 */
	public static void doAddContents(){
		Thread doAddcontents = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try {
						Thread.sleep(1000);
						Object result = getContents();
						int size = contents.size();
						String md5 =null;
						if(result instanceof String){
							md5 = MD5Util.md5Hex(result.toString());
						}else if(result instanceof Image){
							md5 = MD5Util.md5Hex(ImageUtil.image2byte((Image)result));
						}
						contents.add(md5);
						if(contents.size()==size){
							continue;
						}else{
							contentsList.add(result);
						}
						if(contents.size()>10){
							contents.remove(contentsList.get(0));
							contentsList.remove(0);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
		doAddcontents.start();
	}
	
	/**
	 * 
	 * @Function: ToolkitUtil.java
	 * @Description: 给剪切板赋值
	 *
	 * @param: contents
	 * @return：void
	 *
	 * @author: ysf
	 * @date: 2021年2月25日 下午4:58:29 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年2月25日      ysf         给剪切板赋值
	 */
	public static void setContents(Object contents){
		if(contents instanceof String){
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(contents.toString()), null);
		}else if(contents instanceof Image){
			Transferable trans = new Transferable() {
				public DataFlavor[] getTransferDataFlavors() {
					return new DataFlavor[] { DataFlavor.imageFlavor };
				}
				public boolean isDataFlavorSupported(DataFlavor flavor) {
					return DataFlavor.imageFlavor.equals(flavor);
				}
				public Object getTransferData(DataFlavor flavor) {
					if (isDataFlavorSupported(flavor)){
						return (Image)contents;
					}
					return null;
				}
			};
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
		}else if(contents instanceof ImageIcon){
			Transferable trans = new Transferable() {
				public DataFlavor[] getTransferDataFlavors() {
					return new DataFlavor[] { DataFlavor.imageFlavor };
				}
				public boolean isDataFlavorSupported(DataFlavor flavor) {
					return DataFlavor.imageFlavor.equals(flavor);
				}
				public Object getTransferData(DataFlavor flavor) {
					if (isDataFlavorSupported(flavor)){
						return new ImageIcon(((ImageIcon)contents).getDescription()).getImage();
					}
					return null;
				}
			};
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
		}
	}
	/**
	 * 
	 * @Function: ToolkitUtil.java
	 * @Description: 获取剪切板数据
	 *
	 * @param:@return
	 * @return：Object
	 *
	 * @author: ysf
	 * @date: 2021年2月25日 下午4:59:05 
	 *
	 * Modification History:
	 * Date         Author      Description
	 * 2021年2月25日      ysf         获取剪切板数据
	 */
	public static Object getContents(){
		Transferable trans  = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    return text;
                } catch (Exception e) {
                }
            }else if(trans.isDataFlavorSupported(DataFlavor.imageFlavor)){
            	 try {
					Image image = (Image)trans.getTransferData(DataFlavor.imageFlavor);
					return image;
				 }catch (Exception e) {
					// TODO Auto-generated catch block
				 } 
            }
        }
		return null;
	}

}
