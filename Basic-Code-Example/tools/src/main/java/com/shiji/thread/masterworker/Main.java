package com.shiji.thread.masterworker;

import java.util.Map;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Master master = new Master(new PlusWorker(), 3);
		for (int i = 0; i < 10; i++) {
			master.submit(i);//提交任务
		}
		master.execute();//处理任务
		long result = 0;
		Map<String,Object> resultMap = master.getResultMap();//获取任务结果
		while(!resultMap.isEmpty() || !master.isCompleted()){
			Set<String> keys = resultMap.keySet();
			String key = null;
			for(String k : keys){
				key = k;
				break;
			}
			Integer i = null;
			if(key!=null) 
				i = (Integer)resultMap.get(key);
			if(i!=null) 
				result = result + i;//计算
			
			if(key!=null)
				resultMap.remove(key);//移除被计算过的项
		}
		long endTime = System.currentTimeMillis();
		System.out.println("【cost time:"+(endTime - startTime)+"】 result = " + result);
	}
}
