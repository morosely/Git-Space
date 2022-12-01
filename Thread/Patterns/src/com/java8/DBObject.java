package com.java8;

public class DBObject {

	private String classNo;
	private String name;
	private String sutNo;
	public DBObject(String sutNo, String name, String classNo) {
		super();
		this.sutNo = sutNo;
		this.name = name;
		this.classNo = classNo;
	}
	
	public String getUniqueKey(){
		return sutNo+","+name;
	}
	
	public String getClassNo() {
		return classNo;
	}
	public String getName() {
		return name;
	}
	public String getSutNo() {
		return sutNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSutNo(String sutNo) {
		this.sutNo = sutNo;
	}
	
}
