package com.example.myi18n.entity.vo;

import com.example.myi18n.entity.Category;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductsVO {

    private String pname;

    private Double price;

    private String flag;


    private CategoryVO categorys;



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


    public CategoryVO getCategorys() {
        return categorys;
    }

    public void setCategorys(CategoryVO categorys) {
        this.categorys = categorys;
    }
}