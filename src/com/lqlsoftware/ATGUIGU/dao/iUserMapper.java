package com.lqlsoftware.ATGUIGU.dao;

import com.lqlsoftware.ATGUIGU.entity.User;

/**
 * Created by robinlu on 2017/5/8.
 */
public interface iUserMapper {

    public User getUserById(String user_id);

    public User getUserByName(String login_name);

}
