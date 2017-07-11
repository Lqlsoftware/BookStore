package com.lqlsoftware.ATGUIGU.dao;

import com.lqlsoftware.ATGUIGU.entity.Book;

import java.util.List;

/**
 * Created by robinlu on 2017/5/8.
 */
public interface iBookMapper {

    public Book getBookById(String id);

    public void updateBook(Book book);

    public Integer getTotalNum();

    public List<Book> getBookByRowNum(Integer fromNum, Integer pageSize);

    public Integer getTotalByPrice(Integer from, Integer to);

    public List<Book> getBookByPrice(Integer from, Integer to, Integer fromNum, Integer pageSize);

    public Integer getTotalBySearch(String context);

    public List<Book> search(String context, Integer fromNum, Integer pageSize);
}
