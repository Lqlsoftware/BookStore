package com.lqlsoftware.ATGUIGU.entity;

/**
 * Created by robinlu on 2017/7/5.
 */
public class Account {

    // 银行账户id
    private Long account_id;

    // 余额
    private Double balance;

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
