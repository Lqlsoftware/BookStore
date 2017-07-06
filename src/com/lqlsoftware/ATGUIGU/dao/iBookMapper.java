package com.lqlsoftware.ATGUIGU.dao;

import com.lqlsoftware.ATGUIGU.entity.Book;

/**
 * Created by robinlu on 2017/5/8.
 */
public interface iBookMapper {

    public Book getBookById(String id);

    public void updateBook(Book book);
}
