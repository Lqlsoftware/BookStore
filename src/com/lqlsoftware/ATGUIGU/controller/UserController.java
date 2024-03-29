package com.lqlsoftware.ATGUIGU.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.lqlsoftware.ATGUIGU.dao.*;
import com.lqlsoftware.ATGUIGU.entity.*;
import com.sun.org.apache.regexp.internal.RE;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 用户主体Controller
 * 
 * @author Robin Lu
 * 
 */

@Controller
public class UserController {

    @Autowired
    private iUserMapper userMapper;

    // 登陆
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // parameter
        String login_name = request.getParameter("login_name");
        String password = request.getParameter("password");

        User user = userMapper.getUserByName(login_name);
        if (user == null
                || !user.getPassword().equals(password)) {
            request.getSession().setAttribute("errMsg", "登陆失败");
            return "/index";
        }

        request.getSession().setAttribute("user", user);
        return "/main";
    }

    // 跳转
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String _login(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return "/index";
    }

}