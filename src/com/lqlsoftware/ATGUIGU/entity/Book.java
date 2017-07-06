package com.lqlsoftware.ATGUIGU.entity;

import java.util.Date;

/**
 * Created by robinlu on 2017/7/5.
 */
public class Book {

    // 图书id
    private Long id;

    // 作者
    private String author;

    // 标题
    private String title;

    // 价格
    private Double price;

    // 出版日期
    private Date publishing_date;

    // 销售额
    private Long sales_amount;

    // 库存
    private Long store_number;

    // 标记？大致内容？
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getPublishing_date() {
        return publishing_date;
    }

    public void setPublishing_date(Date publishing_date) {
        this.publishing_date = publishing_date;
    }

    public Long getSales_amount() {
        return sales_amount;
    }

    public void setSales_amount(Long sales_amount) {
        this.sales_amount = sales_amount;
    }

    public Long getStore_number() {
        return store_number;
    }

    public void setStore_number(Long store_number) {
        this.store_number = store_number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
