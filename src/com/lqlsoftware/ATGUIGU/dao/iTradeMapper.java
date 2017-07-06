package com.lqlsoftware.ATGUIGU.dao;

import com.lqlsoftware.ATGUIGU.entity.Trade;
import com.lqlsoftware.ATGUIGU.entity.User;

/**
 * Created by robinlu on 2017/5/8.
 */
public interface iTradeMapper {

    public Trade getTradeByUserId(String user_id);

    public Trade getTradeById(String trade_id);

    public void insertTrade(Trade trade);
}
