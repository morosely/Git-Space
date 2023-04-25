package com.shiji.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
	
	static List<JSONObject> sourceList = new ArrayList<JSONObject>();
	static List<DBObject> dbList = new ArrayList<DBObject>();
	static{
		for(int i=0; i<5; i++){
			JSONObject user = new JSONObject(i,"stu+"+i,"name+"+i,i);
			sourceList.add(user);
			if(i < 2){
				DBObject s = new DBObject("stu+"+i,"name+"+i,"classno+"+i);
				dbList.add(s);
			}
		}
	}

	public static void main(String[] args) {
		List<String> uniqueKeyList = dbList.stream().map(DBObject::getUniqueKey).collect(Collectors.toList());
		List updateList = sourceList.stream().filter(user ->uniqueKeyList.contains(user.getUniqueKey())).collect(Collectors.toList());
		System.out.println("更新数据：" + updateList);
		sourceList.removeAll(updateList);
		System.out.println("插入数据：" + sourceList);
		
		
//		List<String> category = array.stream().map(code -> {
//  			return "'" + code + "'";
//  		}).collect(Collectors.toList());
	}
}
