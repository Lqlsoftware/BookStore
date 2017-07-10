package com.lqlsoftware.ATGUIGU.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqlsoftware.ATGUIGU.dao.*;
import com.lqlsoftware.ATGUIGU.entity.Book;
import com.lqlsoftware.ATGUIGU.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 用户主体Controller
 * 
 * @author Robin Lu
 * 
 */

@Controller
public class ShoppingCartController {

    @Autowired
    private iBookMapper bookMapper;

    // 获取购物车信息
    @RequestMapping(value = "/getCart", method = RequestMethod.GET)
    public void getCart(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // 返回的JSON
        JSONObject msg = new JSONObject();
        JSONObject data = new JSONObject();

        // 从session里获取购物车
        Object object = request.getSession().getAttribute("cart");
        Cart cart;
        if (object == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        } else {
            cart = (Cart)object;
        }

        data.put("num", cart.getTotalNum());
        data.put("total", cart.getTotalMoney());
        data.put("queryList", JSONArray.toJSON(cart.getList()));
        msg.put("code", 1);
        msg.put("data", data);
        msg.put("errMsg", "");
        response.getWriter().write(msg.toString());
        return;
    }

    // 添加购物车内书的数量
    @RequestMapping(value = "/addBooksToCart", method = RequestMethod.GET)
    public void addBooksToCart(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // 返回的JSON
        JSONObject msg = new JSONObject();
        JSONObject data = new JSONObject();

        // 参数
        String id = request.getParameter("id");
        String _quantity = request.getParameter("quantity");
        Integer quantity;
        try {
            quantity = Integer.valueOf(_quantity);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            msg.put("code", -1);
            msg.put("data", data);
            msg.put("errMsg", "错误的个数");
            response.getWriter().write(msg.toString());
            return;
        }

        // 从session里获取购物车
        Object object = request.getSession().getAttribute("cart");
        Cart cart;
        if (object == null) {
            cart = new Cart();
        } else {
            cart = (Cart)object;
        }

        Book book = bookMapper.getBookById(id);
        if (book == null) {
            msg.put("code", -1);
            msg.put("data", data);
            msg.put("errMsg", "未找到该书");
            response.getWriter().write(msg.toString());
            return;
        }

        cart.add(book, quantity);
        request.getSession().setAttribute("cart", cart);

        data.put("num", cart.getTotalNum());
        data.put("total", cart.getTotalMoney());
        data.put("queryList", JSONArray.toJSON(cart.getList()));
        msg.put("code", 1);
        msg.put("data", data);
        msg.put("errMsg", (quantity > 0 ? "添加 " : "删除 ") + book.getTitle() + " 成功");
        response.getWriter().write(msg.toString());
        return;
    }


    // 设置购物车内书的数量
    @RequestMapping(value = "/setCart", method = RequestMethod.GET)
    public void setCart(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // 返回的JSON
        JSONObject msg = new JSONObject();
        JSONObject data = new JSONObject();

        // 参数
        String id = request.getParameter("id");
        String _quantity = request.getParameter("quantity");
        Integer quantity;
        try {
            quantity = Integer.valueOf(_quantity);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            msg.put("code", -1);
            msg.put("data", data);
            msg.put("errMsg", "错误的个数");
            response.getWriter().write(msg.toString());
            return;
        }

        // 从session里获取购物车
        Object object = request.getSession().getAttribute("cart");
        Cart cart;
        if (object == null) {
            cart = new Cart();
        } else {
            cart = (Cart)object;
        }

        Book book = bookMapper.getBookById(id);
        if (book == null) {
            msg.put("code", -1);
            msg.put("data", data);
            msg.put("errMsg", "未找到该书");
            response.getWriter().write(msg.toString());
            return;
        }

        cart.add(book, quantity - cart.getBookNum(book.getId()));
        request.getSession().setAttribute("cart", cart);

        data.put("num", cart.getTotalNum());
        data.put("total", cart.getTotalMoney());
        data.put("queryList", JSONArray.toJSON(cart.getList()));
        msg.put("code", 1);
        msg.put("data", data);
        msg.put("errMsg", quantity > 0 ? "添加 "+ book.getTitle() + " 到购物车" : "从购物车删除 "+ book.getTitle());
        response.getWriter().write(msg.toString());
        return;
    }


    // 清空购物车
    @RequestMapping(value = "/clearCart", method = RequestMethod.GET)
    public void clearCart(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // 返回的JSON
        JSONObject msg = new JSONObject();
        JSONObject data = new JSONObject();

        // 从session里获取购物车
        Object object = request.getSession().getAttribute("cart");
        Cart cart = null;
        if (object == null) {
            cart = new Cart();
        } else {
            cart = (Cart)object;
        }
        cart.clear();
        request.getSession().setAttribute("cart", cart);

        data.put("num", cart.getTotalNum());
        data.put("total", cart.getTotalMoney());
        data.put("queryList", JSONArray.toJSON(cart.getList()));
        msg.put("code", 1);
        msg.put("data", data);
        msg.put("errMsg", "");
        response.getWriter().write(msg.toString());
        return;
    }
}