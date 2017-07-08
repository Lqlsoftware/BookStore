package com.lqlsoftware.ATGUIGU.entity;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by robinlu on 2017/7/7.
 */
public class Cart {

    private Map<Long, CartItem> Books;

    public Cart() {
        this.Books = new ConcurrentHashMap<>();
    }

    public void add(Book book, Integer num) {
        if (!hasBook(book.getId()))
            if (num <= 0)
                return;
            else
                Books.put(book.getId(), new CartItem(book, num));
        else {
            CartItem ci = Books.get(book.getId());
            ci.setQuantity(ci.getQuantity() + num);
            if (ci.getQuantity() <= 0)
                del(book.getId());
            else
                Books.put(book.getId(), ci);
        }
    }

    public boolean hasBook(Long id) {
        return Books.containsKey(id);
    }

    public Integer getBookNum(Long id) {
        if (hasBook(id))
            return Books.get(id).getQuantity();
        else
            return 0;
    }

    public Integer getTotalNum() {
        Integer sum = 0;
        for (CartItem values : Books.values())
            sum += values.getQuantity();
        return sum;
    }

    public Double getTotalMoney() {
        Double sum = 0.0;
        for (CartItem values : Books.values())
            sum += values.getTotalMoney();
        return sum;
    }

    public void del(Long id) {
        if (hasBook(id))
            Books.remove(id);
    }

    public void clear() {
        Books.clear();
    }

    public boolean isEmpty() {
        return Books.isEmpty();
    }

    public List<CartItem> getList() {
        List<CartItem> list = new ArrayList<>();
        for (CartItem values : Books.values())
            list.add(values);
        return list;
    }

    public Map<Long, CartItem> getBooks() {
        return Books;
    }
}
