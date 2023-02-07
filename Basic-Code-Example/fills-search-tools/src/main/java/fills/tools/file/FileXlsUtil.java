package fills.tools.file;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * @Author ysf
 * @Description   解析xls工具 //TODO
 * @Param
 * @Date 2020/12/17 17:48
 * @return 
 **/
public class FileXlsUtil {

    public static final String DATE_FOMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @Author ysf
     * @Description   解析xls、xlsx By filePath //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:08
     * @return java.util.List<java.lang.String[]>
     **/
    public static List<String[]> getXlsContentByFilePath(String filePath){
        return getXlsContentByFile(new File(filePath));
    }

    /**
     * @Author ysf
     * @Description   解析xls、xlsx By file //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:08
     * @return java.util.List<java.lang.String[]>
     **/
    public static List<String[]> getXlsContentByFile(File file){
        List<String[]> content =null;
        try {
            content = getXlsContentByInputStream(new FileInputStream(file));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return content;
    }
    /**
     * @Author ysf
     * @Description   解析xls、xlsx By inputStream //TODO
     * @Param  [filePath]
     * @Date 2020/12/18 20:08
     * @return java.util.List<java.lang.String[]>
     **/
    public static List<String[]> getXlsContentByInputStream(InputStream in) throws Exception{
        Workbook wb = WorkbookFactory.create(in);
        List<String[]> listStr =new ArrayList<>();
        if (wb instanceof HSSFWorkbook) {
            listStr = getXlsContent(wb);
        } else if (wb instanceof XSSFWorkbook) {
            listStr = getXlsxContent(wb);
        }
        return listStr;
    }

    /**
     * @Author ysf
     * @Description   解析xls工具 //TODO
     * @Param  [hssfWorkbook]
     * @Date 2020/12/18 20:09
     * @return java.util.List<java.lang.String[]>
     **/
    private static List<String[]> getXlsContent(Workbook hssfWorkbook) {
        List<String[]> resList = new ArrayList<>();
        HSSFSheet hssfSheet = (HSSFSheet) hssfWorkbook.getSheetAt(0);
        // 循环行rows
        for (int rows = 0; rows <= hssfSheet.getLastRowNum(); rows++) {
            HSSFRow hssfRow = hssfSheet.getRow(rows);
            if (hssfRow.getLastCellNum() > 0) {
                String[] values = new String[hssfRow.getLastCellNum()];
                for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
                    values[i] = getValue(hssfRow.getCell(i)).trim();
                }
                resList.add(values);
            }
        }
        return resList;
    }

    /**
     * @Author ysf
     * @Description   解析xlsx工具 //TODO
     * @Param  [xssfWorkbook]
     * @Date 2020/12/18 20:09
     * @return java.util.List<java.lang.String[]>
     **/
    private static List<String[]> getXlsxContent(Workbook xssfWorkbook) {
        List<String[]> resList = new ArrayList<>();
        XSSFSheet xssfSheet = (XSSFSheet) xssfWorkbook.getSheetAt(0);
        // 循环行rows
        for (int rows = 0; rows <= xssfSheet.getLastRowNum(); rows++) {
            XSSFRow xssfRow = xssfSheet.getRow(rows);
            String[] values = new String[xssfRow.getLastCellNum()];
            for (int i = 0; i < xssfRow.getLastCellNum(); i++) {
                values[i] = getValue(xssfRow.getCell(i)).trim();
            }
            resList.add(values);
        }
        return resList;
    }

    /**
     * @Author ysf
     * @Description   数据格式转化 //TODO
     * @Param  [obj]
     * @Date 2020/12/18 20:10
     * @return java.lang.String
     **/
    private static String getValue(Object obj){
        if(obj instanceof  Cell) {
            Cell cell = (Cell)obj;
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            }else if(DateUtil.isCellDateFormatted(cell)){
                Date date = cell.getDateCellValue();
                return new SimpleDateFormat(DATE_FOMAT).format(date);
            }else if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            }else  if (cell.getCellType() == CellType.BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue());
            }
        }
        return "";
    }

    /**
     * @Author ysf
     * @Description   校验xls文件内容 //TODO
     * @Param  [file, macth]
     * @Date 2020/12/19 11:23
     * @return boolean
     **/
    @SuppressWarnings("resource")
	public static boolean checkXlsContentByFile(File file,String macth){
        Workbook wb =null;
        InputStream in =null;
        try {
            in = new FileInputStream(file);
            wb = WorkbookFactory.create(in);
            if (wb instanceof HSSFWorkbook) {
                HSSFSheet hssfSheet = (HSSFSheet) wb.getSheetAt(0);
                // 循环行rows
                for (int rows = 0; rows <= hssfSheet.getLastRowNum(); rows++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rows);
                    if (hssfRow.getLastCellNum() > 0) {
                        for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
                            if(getValue(hssfRow.getCell(i)).contains(macth)){
                                return true;
                            }
                        }
                    }
                }
            } else if (wb instanceof XSSFWorkbook) {
                XSSFSheet xssfSheet = (XSSFSheet) wb.getSheetAt(0);
                // 循环行rows
                for (int rows = 0; rows <= xssfSheet.getLastRowNum(); rows++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rows);
                    for (int i = 0; i < xssfRow.getLastCellNum(); i++) {
                        if(getValue(xssfRow.getCell(i)).contains(macth)){
                            return true;
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage()+""+file.getPath());
        }finally{
        	try {
        		if(wb!=null){
        			wb.close();
        		}
			} catch (Exception e1) {
				
			}
        	try{
	        	if(in!=null){
	        		in.close();
	        	}
        	}catch(Exception e){
        		
        	}
        }

        return false;
    }

    /**
     * 
     * @Title: createXls
     * @Description: 生成xls文件
     * @param title
     * @param titleName
     * @param dataList
     * @author ysf
     * @date 2021年1月11日
     */
    public static HSSFWorkbook createXls(String title,String[] titleName,List<String[]> dataList){
    	//创建Excel文件薄
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建exl
        HSSFSheet sheet=workbook.createSheet(title);
        //创建标题
        HSSFRow row=sheet.createRow(0);
       
        HSSFCell cell=null;
        for (int i=0;i<titleName.length;i++){
            cell=row.createCell(i);
            cell.setCellValue(titleName[i]);
        }
        //追加数据
        for (int i =1;i<=dataList.size();i++){
        	String[] data = dataList.get(i-1);
            HSSFRow nextrow=sheet.createRow(i);
            for(int s=0;s<data.length;s++){
           	 Cell cell2=nextrow.createCell(s);
           	 cell2.setCellValue(data[s]);
           }
        }
        return workbook;
    }
    
    /**
     * 
     * @Title: ceeatXlsx
     * @Description: 生成xlsx文件
     * @param title
     * @param titleName
     * @param dataList
     * @author ysf
     * @date 2021年1月11日
     */
    public static XSSFWorkbook createXlsx(String title,String[] titleName,List<String[]> dataList){
    	XSSFWorkbook workbook=new XSSFWorkbook();
        //创建工作表
        Sheet sheet=workbook.createSheet();
        //创建标题
        Row row=sheet.createRow(0);
        Cell cell=null;
        for (int i=0;i<titleName.length;i++){
            cell=row.createCell(i);
            cell.setCellValue(titleName[i]);
        }
        //追加数据
        for (int i=1;i<=dataList.size();i++){
        	String[] data = dataList.get(i-1);
            Row nextrow=sheet.createRow(i);
            for(int s=0;s<data.length;s++){
            	 Cell cell2=nextrow.createCell(s);
            	 cell2.setCellValue(data[s]);
            }
        }
       return workbook;
    }
    
    /**
     * 
     * @Title: createLocalFile
     * @Description: 生成本地xls文件
     * @param obj
     * @param filePath
     * @author ysf
     * @date 2021年1月11日
     */
    public static void createLocalFile(Object obj,String filePath){
    	 File file=new File(filePath);
         FileOutputStream stream =null;
		try {
			stream = FileUtils.openOutputStream(file);
			if (obj instanceof HSSFWorkbook) {
	             ((HSSFWorkbook) obj).write(stream);
	         } else if (obj instanceof XSSFWorkbook) {
	             ((XSSFWorkbook) obj).write(stream);
	         }
	         stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}finally{
			try{
				if(stream!=null){
					stream.close();
				}
			}catch(Exception e){
				
			}
		}
    }
    
    public static void main(String[] args) {
    	List<String[]> keys = new ArrayList<String[]>();
    	String[] data = new String[]{"1","2","3"};
    	keys.add(data);
    	keys.add(data);
    	keys.add(data);
    	keys.add(data);
    	FileUtil.createFile("D:/fills-entity/test.xlsx");
    	createLocalFile(createXlsx("Test",new String[]{"Test1","Test2","Test3"},keys),"D:/fills-entity/test.xlsx");
	}
}
