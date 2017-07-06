package com.lqlsoftware.ATGUIGU.dao;

import com.lqlsoftware.ATGUIGU.entity.Account;

/**
 * Created by robinlu on 2017/5/8.
 */
public interface iAccountMapper {

    public Account getAccountById(String account_id);

    public Account getAccountByUserId(String user_id);

    public void updateAccount(Account account);
}
