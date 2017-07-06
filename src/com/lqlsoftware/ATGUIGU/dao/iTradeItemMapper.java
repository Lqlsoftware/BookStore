package com.lqlsoftware.ATGUIGU.dao;

import com.lqlsoftware.ATGUIGU.entity.TradeItem;

/**
 * Created by robinlu on 2017/5/8.
 */
public interface iTradeItemMapper {

    public TradeItem getTradeItemByItemId(String item_id);

    public TradeItem getTradeItemByTradeId(String trade_id);

    public void insertTradeItem(TradeItem tradeItem);
}
