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

        if (pageNo == null || pageNo < 1
                || pageSize == null || pageSize < 1) {
            msg.put("code", -1);
            msg.put("data", "");
            msg.put("errMsg", "错误的参数");
            response.getWriter().write(msg.toString());
            return;
        }

        Integer totalNum = bookMapper.getTotalNum();
        if ((pageNo - 1) * pageSize > totalNum) {
            msg.put("code", -1);
            msg.put("errMsg", "参数错误");
            msg.put("data", "");
            response.getWriter().write(msg.toString());
            return;
        }

        List<Book> list = bookMapper.getBookByRowNum((pageNo - 1) * pageSize, pageSize);

        data.put("queryList", JSONArray.toJSON(list));
        data.put("pageNo", pageNo);
        data.put("pageSize", pageSize);
        data.put("pageTotal", totalNum / pageSize + (totalNum % pageSize == 0 ? 0 : 1));
        data.put("isLast", list.size() != pageSize || totalNum == pageNo * pageSize ? 1 : 0);
        msg.put("code", 1);
        msg.put("data", data);
        msg.put("errMsg", "");
        response.getWriter().write(msg.toString());
        return;
    }

    // price 查询书目信息
    @RequestMapping(value = "/getPrice", method = RequestMethod.GET)
    public void getPrice(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // 返回的JSON
        JSONObject msg = new JSONObject();
        JSONObject data = new JSONObject();

        // 请求参数
        Integer from = Integer.valueOf(request.getParameter("from"));
        Integer to = Integer.valueOf(request.getParameter("to"));
        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));

        if (from == null || from < 1
                || to == null || to < 1
                || pageNo == null || pageNo < 1
                || pageSize == null || pageSize < 1) {
            msg.put("code", -2);
            msg.put("data", "");
            msg.put("errMsg", "参数错误");
            response.getWriter().write(msg.toString());
            return;
        }

        Integer totalNum = bookMapper.getTotalByPrice(from, to);
        if ((pageNo - 1) * pageSize > totalNum) {
            msg.put("code", -3);
            msg.put("errMsg", "参数错误");
            msg.put("data", "");
            response.getWriter().write(msg.toString());
            return;
        }

        List<Book> list = bookMapper.getBookByPrice(from, to, (pageNo - 1) * pageSize, pageSize);

        data.put("queryList", JSONArray.toJSON(list));
        data.put("pageNo", pageNo);
        data.put("pageSize", pageSize);
        data.put("pageTotal", totalNum / pageSize + (totalNum % pageSize == 0 ? 0 : 1));
        data.put("isLast", list.size() != pageSize || totalNum == pageNo * pageSize ? 1 : 0);
        msg.put("code", 1);
        msg.put("data", data);
        msg.put("errMsg", "");
        response.getWriter().write(msg.toString());
        return;
    }
}