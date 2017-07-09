package com.lqlsoftware.ATGUIGU.utils;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by robinlu on 2017/7/9.
 */
public class TradeInfo {

    private Date trade_time;

    private ConcurrentLinkedDeque<TradeItemInfo> collection;

    public TradeInfo(Date trade_time) {
        this.trade_time = trade_time;
        this.collection = new ConcurrentLinkedDeque<>();
    }

    public Date getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(Date trade_time) {
        this.trade_time = trade_time;
    }

    public void add(TradeItemInfo info) {
        this.collection.add(info);
    }

    public ConcurrentLinkedDeque<TradeItemInfo> getCollection() {
        return collection;
    }
}