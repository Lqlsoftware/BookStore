package com.lqlsoftware.ATGUIGU.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 
 * 用户主体Controller
 * 
 * @author Robin Lu
 * 
 */

@Controller
public class BookController {

    // page 查看书目信息
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public void getPage(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {

        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // 返回的JSON
        JSONObject msg = new JSONObject();
        JSONObject data = new JSONObject();
    }
}