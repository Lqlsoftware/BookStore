package com.lqlsoftware.ATGUIGU.entity;

/**
 * Created by robinlu on 2017/7/7.
 */
public class CartItem {

    private Book book;

    private Integer quantity;

    CartItem (Book book, Integer quantity) {
        this.book = book;
        this.quantity = quantity > 0 ? quantity : 1;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalMoney() {
        return this.book.getPrice() * this.quantity;
    }
}
