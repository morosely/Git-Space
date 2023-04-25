package com.shiji.java8;

public class JSONObject {

	private Integer age;
	private Integer id;
	private String name;
	private String sutNo;
	
	public JSONObject(Integer id,String sutNo,String name, Integer age) {
		super();
		this.age = age;
		this.id = id;
		this.name = name;
		this.sutNo = sutNo;
	}
	
	public String getUniqueKey(){
		return sutNo+","+name;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public String getSutNo() {
		return sutNo;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSutNo(String sutNo) {
		this.sutNo = sutNo;
	}

	@Override
	public String toString() {
		return "JSONObject [age=" + age + ", id=" + id + ", name=" + name + ", sutNo=" + sutNo + "]";
	}

}
