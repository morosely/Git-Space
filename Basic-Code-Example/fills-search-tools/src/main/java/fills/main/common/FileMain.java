package fills.main.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
/**
 * 
 * @ClassName: FileMain.java
 * @Description: 文件面板
 * @author: ysf
 * @date: 2021年3月22日 上午11:32:43 
 *
 * Modification History:
 * Date         Author         Description
 * 2021年3月22日      ysf            文件面板
 */
public class FileMain {
	
	//上传文件窗口
    static JFrame fileFrame = new JFrame();

    //上传文件面板
    static JPanel filePanel = new JPanel();

    //文件选择器
    static JFileChooser jFileChooser = new JFileChooser();
    
    //需要更新的文本内容
    static JTextField filePath = null;
    
    //需要更新的文本标签
    static JLabel fileLabel = null;
    
    static String actionType =null;
    
    
    /**
     * 初始化组件
     */
    static {
    	initJframe();initJpanel();doListener();addModule();
    }
    
    //初始化窗口
    private static void initJframe(){
        fileFrame.setSize(500,370);
        fileFrame.setName("文件窗口");
        fileFrame.setLocationRelativeTo(null);
        JFrameMain.setLogo(fileFrame,JFrameMain.commonBase64);
		JFrameMain.setUndecorated(fileFrame);
		JFrameMain.setResizable(fileFrame);
		JFrameMain.setWindowsStyle(fileFrame, JRootPane.INFORMATION_DIALOG);
    }
    
    //初始化面板
    private static void initJpanel(){
        filePanel.setSize(500,370);
        filePanel.setName("文件面板");
    }
    
    /**
     * 
     * @Function: Db2WordMain.java
     * @Description: 添加组件
     *
     * @param:
     * @return：void
     *
     * @author: ysf
     * @date: 2021年1月25日 下午2:03:07 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年1月25日      ysf         添加组件
     */
    private static void addModule(){
        filePanel.add(jFileChooser);
        fileFrame.add(filePanel);
    }
    
    /**
     * 
     * @Function: FileMain.java
     * @Description: 设置Text
     *
     * @param: filePath
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月20日 下午3:07:57 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月20日      ysf         设置Text
     */
    public static void setJTextField(JTextField filePath){
    	FileMain.filePath = filePath;
    }
    
    /**
     * 
     * @Function: FileMain.java
     * @Description: 设置Label
     *
     * @param: fileLabel
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月20日 下午3:11:08 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月20日      ysf         设置Label
     */
    public static void setJLabel(JLabel fileLabel){
    	FileMain.fileLabel = fileLabel;
    }
    
    /**
     * 
     * @Function: FileMain.java
     * @Description: 处理类型
     *
     * @param: actionType
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月25日 下午7:53:04 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月25日      ysf         处理类型
     */
    public static void setActionType(String actionType){
    	FileMain.actionType = actionType;
    }
    
    
   /**
    * 
    * @Function: FileMain.java
    * @Description: 设置访问文件类型 JFileChooser.FILES_ONLY(0)-只访问文件，JFileChooser.DIRECTORIES_ONLY(1)-只访问目录，JFileChooser.FILES_AND_DIRECTORIES(2)-文件和目录都可访问
    *
    * @param:
    * @return：void
    *
    * @author: ysf
    * @date: 2021年3月20日 下午3:29:21 
    *
    * Modification History:
    * Date         Author      Description
    * 2021年3月20日      ysf         设置访问文件类型 
    */
    public static void setFileType(int type){
    	jFileChooser.setFileSelectionMode(type);
    }
    
    /**
     * 
     * @Function: FileMain.java
     * @Description: 设置选择文件类型
     *
     * @param: suffix
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月20日 下午3:46:21 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月20日      ysf         设置选择文件类型
     */
    public static void setFileFilter(String suffix){
    	jFileChooser.addChoosableFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "获取(*."+suffix+")文件";
			}
			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				if(f.getName().endsWith(suffix)){
					return true;
				}
				return false;
			}
		});
    }
    
    /**
     * 
     * @Function: FileMain.java
     * @Description: 添加监听事件
     *
     * @param:
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月20日 下午3:09:33 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月20日      ysf         添加监听事件
     */
    private static void doListener(){
        jFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(jFileChooser.getSelectedFile()!=null){
            		if(filePath!=null){
    	            	filePath.setText(jFileChooser.getSelectedFile().getPath());
    	            }
    	            if(fileLabel!=null){
    	            	fileLabel.setText(jFileChooser.getSelectedFile().getPath());
    	            }
            	}
	            fileFrame.setVisible(false);
            }
        });
    }
    
    /**
     * 
     * @Function: FileMain.java
     * @Description: 文件窗口显示
     *
     * @param:
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月20日 下午3:14:36 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月20日      ysf         文件窗口显示
     */
    public static void showFileJFrame(){
    	if(!fileFrame.isVisible()){
	    	fileFrame.validate();
	    	fileFrame.setVisible(true);
    	}
    }
    
    /**
     * 
     * @Function: FileMain.java
     * @Description: 文件窗口隐藏
     *
     * @param:
     * @return：void
     *
     * @author: ysf
     * @date: 2021年3月20日 下午3:16:37 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年3月20日      ysf         文件窗口隐藏
     */
    public static void hideFileJFrame(){
    	if(fileFrame.isVisible()){
	    	fileFrame.setVisible(false);
    	}
    }
    
    /**
     * 
     * @Function: FileMain.java
     * @Description: 窗口置顶
     *
     * @param:
     * @return：void
     *
     * @author: ysf
     * @date: 2021年4月16日 下午2:31:19 
     *
     * Modification History:
     * Date         Author      Description
     * 2021年4月16日      ysf         窗口置顶
     */
    public static void showTop(){
    	fileFrame.setAlwaysOnTop(true);
    }
    
    
    public static void main(String[] args) {
    	showFileJFrame();
	}
    
}

 
