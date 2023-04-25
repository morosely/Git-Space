package com.shiji.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * ConcurrentModificationException异常
 * @author Administrator
 *
 */
public class TestList {

	public static void main(String[] args) {

		List<String> myList = new ArrayList<String>();
		myList.add("1");
		myList.add("2");
		myList.add("3");
		myList.add("4");
		myList.add("5"); 
		
		List<String> linkList = new LinkedList<String>();
		linkList.add("A");
		linkList.add("B");
		linkList.add("C");
		linkList.add("D");
		linkList.add("E");
	
		 // 1 使用Iterator提供的remove方法，用于删除当前元素  
		/*for (Iterator<String> it = myList.iterator(); it.hasNext();) {  
		     String value = it.next();  
		      if (value.equals( "3")) {  
		          it.remove();  // ok  
		     }  
		}  
		System. out.println( "List Value:" + myList.toString());  */
		  
		 // 2 建一个集合，记录需要删除的元素，之后统一删除               
		/*List<String> templist = new ArrayList<String>();  
		for (String value : myList) {  
		      if (value.equals( "3")) {  
		          templist.add(value);  
		     }  
		}  
		 // 可以查看removeAll源码，其中使用Iterator进行遍历  
		myList.removeAll(templist);  
		System. out.println( "List Value:" + myList.toString());  */        
		  
		// 3. 使用线程安全CopyOnWriteArrayList进行删除操作  
		/*List<String> list = new CopyOnWriteArrayList<String>();  
		list.add("1");  
		list.add("2");  
		list.add("3");  
		list.add("4");  
		list.add("5");  
		  
		Iterator<String> it = list.iterator();  
		  
		 while (it.hasNext()) {  
		     String value = it.next();  
		      if (value.equals( "3")) {  
		    	  list.remove( "4");  
		    	  list.add( "6");  
		    	  list.add( "7");  
		     }  
		}  
		System. out.println( "List Value:" + list.toString());*/  
		  
		 // 4. 不使用Iterator进行遍历，需要注意的是自己保证索引正常  
		 /*for ( int i = myList.size()-1; i >= 0; i--) {  
		     String value = myList.get(i);  
		     System. out.println( "List Value:" + value);  
		      if (value.equals( "3")) {  
		          myList.remove(value);  // ok  
		     }  
		}  
		System. out.println( "List Value:" + myList.toString()); */ 
    }
}
