package com.example.myi18n.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@NoArgsConstructor
@AllArgsConstructor
public class Products {
    private Integer pid;

    private String pname;

    private Double price;

    private String flag;

    private Integer categoryId;

    @Transient
    private Category categorys;


    public Category getCategorys() {
        return categorys;
    }

    public void setCategorys(Category categorys) {
        this.categorys = categorys;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Products(Integer pid, String pname, Double price, String flag, Integer categoryId) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.flag = flag;
        this.categoryId = categoryId;
    }
}