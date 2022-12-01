package cn.itcast.orm.test.entity;

import java.math.BigDecimal;
import java.util.Date;

//实体类：图书

import cn.itcast.orm.annotation.ORMColumn;
import cn.itcast.orm.annotation.ORMId;
import cn.itcast.orm.annotation.ORMTable;

@ORMTable(name = "t_book")
public class Book {

    @ORMId
    @ORMColumn(name = "bid")
    private Integer id;

    @ORMColumn(name = "bname")
    private String name;

    @ORMColumn(name = "author")
    private String author;

    @ORMColumn(name = "price")
    private BigDecimal price;
    
    @ORMColumn(name = "publishDate")
    private Date publishDate;

    public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
