package com.omdmain.pojo;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;
/****
 * @Author:shenkunlin
 * @Description:AeonDelCommand构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="aeon_del_command")
public class AeonDelCommand implements Serializable{

    @Column(name = "shopCode")
	private String shopCode;//

    @Column(name = "sql")
	private String sql;//

    @Column(name = "updateDate")
	private Date updateDate;//



	//get方法
	public String getShopCode() {
		return shopCode;
	}

	//set方法
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	//get方法
	public String getSql() {
		return sql;
	}

	//set方法
	public void setSql(String sql) {
		this.sql = sql;
	}
	//get方法
	public Date getUpdateDate() {
		return updateDate;
	}

	//set方法
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


}
