package com.shiji.java8;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
@Slf4j
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
		List updateList = sourceList.stream().filter(user -> uniqueKeyList.contains(user.getUniqueKey())).collect(Collectors.toList());
		System.out.println("更新数据：" + updateList);
		sourceList.removeAll(updateList);
		System.out.println("插入数据：" + sourceList);
		
		
//		List<String> category = array.stream().map(code -> {
//  			return "'" + code + "'";
//  		}).collect(Collectors.toList());

		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				try {
					log.debug("{}", sdf.parse("1951-04-21"));
				} catch (Exception e) {
					log.error("{}", e);
				}
			}).start();
		}*/

		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				LocalDateTime now = LocalDateTime.now();
				String formattedDate = now.format(formatter);
				log.debug("A.----->>> 当前时间并格式化为字符串:{} -> {}", now,formattedDate);

				String dateStr = "2024-01-01 12:12:12";
				LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
				log.debug("B.----->>> 字符串转换为日期时间:LocalDateTimed对象:{} -> {}", localDateTime,localDateTime.format(formatter));

			}).start();
		}
	}
}
