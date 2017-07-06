package com.lqlsoftware.ATGUIGU.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by robinlu on 2017/7/6.
 */
@Controller
public class JumpController {

    @RequestMapping(value = "/")
    public String login(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return "/index";
    }


}
