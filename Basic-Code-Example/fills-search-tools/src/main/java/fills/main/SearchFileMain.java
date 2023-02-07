package fills.main;


import java.awt.Choice;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import fills.main.common.FileMain;
import fills.main.common.JFrameMain;
import fills.thread.ExecutorUtil;
import fills.thread.FileSearchThreadUtil;
import fills.tools.file.FileUtil;
import fills.tools.jna.ToolkitUtil;

/**
 * @Author ysf
 * @Description   文件及文件内容查找 //TODO
 * @Param
 * @Date 2020/12/19 14:12
 * @return
 **/
public class SearchFileMain {
    //创建窗口
    static JFrame jFrame ;
    //创建面板
    static JPanel jPanel = new JPanel();
    //创建表格
    static JTable jTable = new JTable();
    //滚动窗口
    static JScrollPane scrollPane=new JScrollPane(jTable);
    //查询结果
    static volatile Set<String> searchFileResult = Collections.synchronizedSet(new HashSet<>());
    //文件路径
    static JTextField filePath = new JTextField();
    //查询匹配内容
    static JTextField searchMacth = new JTextField();
    //查询按钮
    static JButton jButton = new JButton("search");
    //选择文件按钮
    static JButton jselect = new JButton("选择目录");
    //单选按钮
    static ButtonGroup group=new ButtonGroup();
    //单选按钮
    static JRadioButton folder=new JRadioButton("文件名",false);
    //单选按钮
    static JRadioButton file=new JRadioButton("文件内容",false);
  
    
    //检查文件下拉框
    static Choice suffixItem = new Choice();

    //查询完成标识
    static volatile Boolean flag = true;
    //初始化线程池
    static ThreadPoolExecutor executorService;
    //记录检索文件个数
    static int count =0;

    static String actionType="文件名";

    //初始化窗口
    public static void initJframe(){
        jFrame  = JFrameMain.getJFrame("search file", "查找文件询窗口", 800,600);
		JFrameMain.setLogo(jFrame,JFrameMain.commonBase64);
		JFrameMain.setUndecorated(jFrame);
		JFrameMain.setResizable(jFrame);
		JFrameMain.setWindowsStyle(jFrame, JRootPane.INFORMATION_DIALOG);

    }

    //初始化面板
    public static void initJpanel(){
        jPanel.setPreferredSize(new Dimension(800,600));
        jPanel.setName("查询面板");
    }
    
    //滑动窗口
    public static void initScrollPane(){
        scrollPane.setName("滑动窗口");
    }
    
    //初始化table
    public static void initTable(){
        jTable.setPreferredScrollableViewportSize(new Dimension(760,500));
        //修改表头行高
        jTable.getTableHeader().setPreferredSize(new Dimension(jTable.getTableHeader().getWidth(), 25));
        jTable.setRowHeight(25);
        jTable.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int count=jTable.getSelectedRow();//获取你选中的行号（记录）
                String content = jTable.getValueAt(count, 0).toString();
                if(content.length()>0){
                	ToolkitUtil.setContents(content);
                }
            }
		});
    }
    
    //初始化单选按钮
    public static void initRiado(){
        group.add(folder);
        group.add(file);
        addListener(folder);
        addListener(file);
    }
    
    //初始化类对象方法
  	public static void initChoice(){
  		suffixItem.add("jsp,js,html,css");
  		suffixItem.add("log,txt,ini");
  		suffixItem.add("java,php,py,prj");
  		suffixItem.add("sh,bat,cmd,bin");
  		suffixItem.add("hex,cpp,hpp");
  		suffixItem.add("JPG,JPEG,jpg,jpeg");
  		suffixItem.add("PNG,png");
  		suffixItem.add("GIF,gif");
  		suffixItem.add("doc,DOC");
  		suffixItem.add("docx,DOCX");
  		suffixItem.add("xls,xlsx");
  		suffixItem.add("ppt,PPT");
  		suffixItem.add("pptx,PPTX");
  		suffixItem.add("pdf,PDF");
  		suffixItem.add("properties,dat,xml");
    }
  	
    //单选按钮添加监听
    public static void addListener(JRadioButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Object obj = e.getSource();
                if(obj instanceof JRadioButton ){
                    JRadioButton jRadioButton = (JRadioButton) obj;
                    actionType = jRadioButton.getText();
                }
            }
        });
    }
    
    //初始化输入框
    public static void initText(){
        filePath.setColumns(14);
        searchMacth.setColumns(6);
    }
    
    //button 添加监听事件
    public static void initButton(){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                File file = new File(filePath.getText());
                String macth = searchMacth.getText();
                if(macth!=null) {
                    Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							executorService = ExecutorUtil.getThreadPoolExecutor(100,100,"Fills-SearchFile-Thread-%d");
							FileUtil.doActionFile();
							initSearchData();
							doSearchFile(file,macth);
							System.out.println("查找文件数:"+count);
							flag = false;
						}
					});
                    t.start();
                }
            }
        });
        jselect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FileMain.setFileType(2);
                FileMain.setJTextField(filePath);
                FileMain.showFileJFrame();
            }
        });
    }
    
    //初始化查询面板
    public static void initSearchData(){
    	System.gc();
        count=0;
        flag = true;
        searchFileResult.clear();
        setTableData();
    }

    //封装table数据
    public static void setTableData(){
    	long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                    	setTableData2(start);
                        long completedCount = executorService.getCompletedTaskCount();
                        long taskCount = executorService.getTaskCount();
                        int activeCount = executorService.getActiveCount();
                        if(!flag && completedCount == taskCount && taskCount == count){
                        	System.out.println("线程任务总数:"+taskCount+",当前线程活跃数量:"+activeCount+",当前完成线程数量:"+completedCount);
                            return;
                        }
                        Thread.sleep(1000);
                    }
                }catch (Exception e){
                }
            }
        });
        thread.start();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void setTableData2(long start){
		 Vector searchFileData = new Vector();
         Vector columnName = new Vector();
         columnName.add("查询文件结果【查找文件总数："+count+ ",查询时间：" +((System.currentTimeMillis() - start)/1000) + "秒"+",命中文件数: "+searchFileResult.size()+"】");
         System.out.println("线程任务总数:"+executorService.getTaskCount()+",当前线程活跃数量:"+executorService.getActiveCount()+",当前完成线程数量:"+executorService.getCompletedTaskCount());
         TableModel tableModel = jTable.getModel();
         DefaultTableModel defaultTableModel =null;
         if(tableModel != null){
             if(tableModel instanceof  DefaultTableModel){
             	defaultTableModel =  (DefaultTableModel)tableModel;
             	defaultTableModel.getDataVector().clear();
             }
         }
         if(defaultTableModel==null){
         	defaultTableModel = new DefaultTableModel(searchFileData, columnName);
         }else{
         	defaultTableModel.setDataVector(searchFileData, columnName);
         }
         Set<String> sortSet = new TreeSet<>((o1, o2) -> o2.compareTo(o1));
         sortSet.addAll(searchFileResult);
         for (String searchFile : sortSet) {
             Vector rowName = new Vector();
             rowName.add(searchFile);
             searchFileData.add(rowName);
         }
         jTable.setModel(defaultTableModel);
    }
    
    
    //添加面板组件
    public static void addModule(){
        jFrame.add(jPanel);
        jPanel.add(jselect);
        jPanel.add(filePath);
        jPanel.add(new JLabel("查询内容:"));
        jPanel.add(searchMacth);
        jPanel.add(folder);
        jPanel.add(file);
        jPanel.add(suffixItem);
        jPanel.add(jButton);
        jPanel.add(scrollPane);
    }

    //主函数入口
    public static void main(String[] args) {
        initJframe();
        initJpanel();
        initScrollPane();
        initTable();
        initText();
        initRiado();
        initButton();
        initChoice();
        addModule();
        JFrameMain.show(jFrame);
    }

    /**
     * 开始查找文件
     * @param file
     * @param macth 匹配内容
     */
    public static  void doSearchFile(File file, String macth){
        if(file!=null){
        	if("文件名".equals(actionType)){
        		checkResult(file,macth,false);
        	}
            if(file.isDirectory()){
                File[] listFile = file.listFiles();
                if(listFile!=null) {
                    for (File f : listFile) {
                        doSearchFile(f, macth);
                    }
                }
            }else if(file.isFile()){
            	if("文件内容".equals(actionType)) {
                    checkResult(file,macth,true);
                }
                return;
            }

        }
    }
    
    
    /**
     * 多线程校验目标文件
     * @param file
     * @param macth 匹配内容
     * @param flag 是否过滤文件
     */
    public static void checkResult(File file, String macth,boolean flag){
    	String suffixKey = suffixItem.getSelectedItem();
    	String fileStr="";
    	if(flag){
	    	String filePath = file.getPath();
	    	fileStr = filePath.substring(filePath.lastIndexOf(".")+1,filePath.length());
	    	if(!suffixKey.contains(fileStr)){
	    		return;
	    	}
    	}
    	count++;
    	FileSearchThreadUtil fileSearchThreadUtil = new FileSearchThreadUtil(file,macth,searchFileResult,actionType,fileStr);
		executorService.execute(fileSearchThreadUtil);
        
    }
    
    
}
