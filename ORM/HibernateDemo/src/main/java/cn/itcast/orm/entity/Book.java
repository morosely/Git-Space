package cn.itcast.orm.entity;

import javax.persistence.*;

//实体类：图书
@Entity
@Table(name = "t_book")
public class Book {

    @Id
    @Column(name = "bid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   //主键

    @Column(name="bname")
    private String name;  //图书名字

    @Column(name="author")
    private String author; //图书作者

    @Column(name="price")
    private double price; //图书价格

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}
