package com.lqlsoftware.ATGUIGU.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.lqlsoftware.ATGUIGU.dao.iBookMapper;
import com.lqlsoftware.ATGUIGU.dao.iUserMapper;
import com.lqlsoftware.ATGUIGU.entity.Book;
import com.lqlsoftware.ATGUIGU.entity.Cart;
import com.lqlsoftware.ATGUIGU.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    @Autowired
    private iBookMapper bookMapper;

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
        Cart cart = null;
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

    // 获取购物车信息
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
        Cart cart = null;
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
        msg.put("errMsg", "");
        response.getWriter().write(msg.toString());
        return;
    }
}