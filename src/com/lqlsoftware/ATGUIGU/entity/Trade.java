package com.lqlsoftware.ATGUIGU.entity;

import java.util.Date;

/**
 * Created by robinlu on 2017/7/5.
 */
public class Trade {

    // 交易流水号
    private Long trade_id;

    // 用户id
    private Long user_id;

    // 交易时间
    private Date trade_time;

    public Long getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(Long trade_id) {
        this.trade_id = trade_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(Date trade_time) {
        this.trade_time = trade_time;
    }
}
