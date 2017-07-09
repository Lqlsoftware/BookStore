package com.lqlsoftware.ATGUIGU.utils;

/**
 * Created by robinlu on 2017/7/9.
 */
public class TradeItemInfo {

    private String title;

    private Double price;

    // 交易数量
    private Long quantity;

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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
