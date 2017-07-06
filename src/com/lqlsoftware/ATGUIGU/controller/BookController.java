package com.lqlsoftware.ATGUIGU.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqlsoftware.ATGUIGU.dao.iBookMapper;
import com.lqlsoftware.ATGUIGU.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 
 * 用户主体Controller
 * 
 * @author Robin Lu
 * 
 */

@Controller
public class BookController {

    @Autowired
    private iBookMapper bookMapper;

    // page 查看书目信息
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public void getPage(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // 返回的JSON
        JSONObject msg = new JSONObject();
        JSONObject data = new JSONObject();

        // 请求参数
        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));

        Integer totalNum = bookMapper.getTotalNum();
        if ((pageNo - 1) * pageSize > totalNum) {
            msg.put("code", -1);
            msg.put("data", "");
            msg.put("errMsg", "错误的页数");
            response.getWriter().write(msg.toString());
            return;
        }

        List<Book> list = bookMapper.getBookByRowNum((pageNo - 1) * pageSize, pageSize);

        data.put("queryList", JSON.toJSON(list));
        data.put("pageNo", pageNo);
        data.put("pageSize", pageSize);
        data.put("pageTotal", totalNum % pageSize);
        data.put("isLast", list.size() != pageSize || totalNum == pageNo * pageSize ? true : false);
        msg.put("code", 1);
        msg.put("data", data);
        msg.put("errMsg", "");
        response.getWriter().write(msg.toString());
        return;
    }
}