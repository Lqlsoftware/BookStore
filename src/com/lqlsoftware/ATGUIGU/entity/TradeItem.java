package com.lqlsoftware.ATGUIGU.entity;

/**
 * Created by robinlu on 2017/7/5.
 */
public class TradeItem {

    // 交易物品的流水号
    private Long item_id;

    // 图书id
    private Long book_id;

    // 交易数量
    private Long quantity;

    // 交易流水号
    private Long trade_id;

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(Long trade_id) {
        this.trade_id = trade_id;
    }
}
