package fills.tools.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @ClassName: FileJavaUtil.java
 * @Description: java 类解析工具
 * @author: ysf
 * @date: 2021年4月7日 上午10:58:25 
 *
 * Modification History:
 * Date         Author         Description
 * 2021年4月7日      ysf             java 类解析工具
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FileJavaUtil {
	private static String packagePath = "com.ddky";
	
	public static void main(String[] args) {
		//getJavaContent("D:/fills-product/fillsJavaTools/src/main/java/fills/tools/voice/VoiceUtil.java");
	}
	
	
	public static void getJavaContent(String filePath){
		List<String> listContents = fileContentListByFilePath(filePath); 
		Map<String,List<String>> importMap = new HashMap<String, List<String>>();
		Map<String,List> tempMap = new HashMap<String, List>();
		Map<String,List> methodMap = new LinkedHashMap<String, List>();
		List<String> attributeList = new ArrayList<String>();
		for(String rowContent : listContents){
			importJava(rowContent, importMap);
		}
		//切割方法
		if(methodJava(listContents, tempMap)){
			methodJava(listContents, tempMap, methodMap);
		}
		for(Map.Entry<String, List> entry : methodMap.entrySet()){
			System.out.println(entry.getKey());
			/*for(Object o:entry.getValue()){
				System.out.println(o.toString());
			}*/
		}
		//切割属性
		if(methodJava(listContents, tempMap)){
			attributeJava(listContents, tempMap, attributeList);
		}
		for(String s:attributeList){
			System.out.println(s);
		}
	}
	public static boolean methodJava(List<String> listContents,Map<String,List> tempMap,Map<String,List> methodMap){
		List se= tempMap.get("se");
		List<Integer> rows = tempMap.get("rows");
		if("{".equals(se.get(0))&&"}".equals(se.get(se.size()-1))){
			se.remove(0);
			se.remove(se.size()-1);
			rows.remove(0);
			rows.remove(rows.size()-1);
		}else{
			return false;
		}
		int r =0,start=0;
		for(int i=0;i<se.size();i++){
			if("{".equals(se.get(i))){
				r++;
				continue;
			}else if("}".equals(se.get(i))){
				r--;
			}
			if(r==0){
				methodMap.put(methodName(listContents.get(rows.get(start)))+"_"+rows.get(start), listContents.subList(rows.get(start), rows.get(i)+1));
				start=i+1;
			}
		}
		return true;
	}
	
	
	public static void attributeJava(List<String> listContents,Map<String,List> tempMap,List<String> attributeList){
		List se= tempMap.get("se");
		List<Integer> rows = tempMap.get("rows");
		int start = rows.get(0)+1,end=0;
		if("{".equals(se.get(0))&&"}".equals(se.get(se.size()-1))){
			attributeList.addAll(listContents.subList(rows.get(rows.size()-2)+1, rows.get(rows.size()-1)));
			se.remove(0);
			se.remove(se.size()-1);
			rows.remove(0);
			rows.remove(rows.size()-1);
		}
		int r =0;
		for(int i=0;i<se.size();i++){
			if("{".equals(se.get(i))){
				if(r==0){
					end  =rows.get(i);
					if(start<end){
						System.out.println("start:"+(start+1)+"end:"+end);
						attributeList.addAll(listContents.subList(start, end));
					}
				}
				r++;
			}else if("}".equals(se.get(i))){
				r--;
				if(r==0){
					start = rows.get(i)+1;
				}
			}
		}
	}
	
	public static String methodName(String rowContent){
		String[] cells = rowContent.trim().substring(0, rowContent.lastIndexOf("(")).split(" ");
		return cells[cells.length-1];
	}
	
	public static boolean methodJava(List<String> listContents,Map<String,List> methodMap){
		String rowContents = null;
		List<String> se= new ArrayList<String>();
		List<Integer> rows = new ArrayList<Integer>();
		for(int i=0;i<listContents.size();i++){
		  	rowContents =  listContents.get(i).trim();
		  	for(int j=0;j<rowContents.length();j++){
		  		String r= checkStartAndEnd(rowContents.charAt(j));
		  		if("0".equals(r)){
		  			continue;
		  		}
		  		se.add(r);
		  		rows.add(i);
		  	}
		}
		if(se.size()== rows.size()&&se.size()>0){
			methodMap.put("se", se);
			methodMap.put("rows", rows);
			return true;
		}
		return false;
	}
	
	
	
	public static boolean importJava(String rowContent,Map<String,List<String>> map){
		if(rowContent.trim().startsWith("import")){
			List<String> importList = map.get("importList");
			if(importList ==null){
				importList = new ArrayList<String>();
				map.put("importList", importList);
			}
			String[] importArray = rowContent.replaceAll("import", "").split(";");
			for(String str : importArray){
				if(str.trim().startsWith(packagePath)){
					importList.add(str.trim());
				}
			}
		}
		return false;
	}
	
	
	
	public static String checkStartAndEnd(char c){
		if(c=='}'){
			return "}";
		}else if(c=='{'){
			return "{";
		}
		return "0";
	}
	
	
	
	public static List<String> fileContentListByFilePath(String filePath){
    	List<String> contents = new ArrayList<String>();
    	FileReader fileReader =null;
    	BufferedReader bufferedReader =null;
    	try {
    		fileReader = new FileReader(filePath);
    		bufferedReader  = new BufferedReader(fileReader);
            String temp = "";
            String rowContents = "";
            while ((temp = bufferedReader.readLine()) != null) {
            	if(temp.trim().startsWith("/**")||temp.trim().startsWith("*")||temp.trim().startsWith("//")||temp.trim().startsWith("@")||temp.trim().length()==0){
    				continue;
    			}
            	String[] temps = temp.split("//");
            	if(temps.length>1){
            		temp = temps[0];
            	}
            	if(temp.trim().equals("{")||temp.trim().equals(";")){
            		rowContents += temp;
            		temp = null;
            		continue;
            	}else if(!temp.trim().endsWith("{")&&!temp.trim().endsWith("}")&&!temp.trim().endsWith(";")){
            		if(rowContents.endsWith(";")||rowContents.endsWith("{")||rowContents.contains("//")){
            			rowContents +="\r\n"+ temp;
            		}else{
            			rowContents += temp;
            		}
            		temp = null;
            		continue;
            	}else if(temp.trim().startsWith(",")||temp.trim().startsWith("\"")||temp.trim().startsWith("+")||temp.trim().startsWith("||")||temp.trim().startsWith("&&")||temp.trim().startsWith("|")||temp.trim().startsWith("&")){
            		rowContents += temp;
            		continue;
            	}else{
            		if(rowContents.length()==0){
            			rowContents = temp;
            		}else{
            			rowContents +="\r\n"+ temp;
            		}
            	}
            	while(rowContents.indexOf("  ")>-1||rowContents.indexOf("	")>-1){
            		rowContents = rowContents.replaceAll("  ", " ").replaceAll("	", " ");
            	}
            	if(rowContents.startsWith(" ")){
            		rowContents =  rowContents.substring(1, rowContents.length());
            	}
            	rowContents = rowContents.replaceAll("\\}\\{", "}\r\n{").replaceAll("\\} \\{", "} \r\n {");
            	contents.add(rowContents);
            	temp = "";
            	rowContents = "";
            }
            bufferedReader.close();
        }catch (Exception e){
             System.out.println(e.getMessage());
        }finally{
        	try {
				if(fileReader!=null){
					fileReader.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        	try {
				if(bufferedReader!=null){
					bufferedReader.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        }
    	return contents;
    }

}
