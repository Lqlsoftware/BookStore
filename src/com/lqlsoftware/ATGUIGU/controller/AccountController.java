package com.lqlsoftware.ATGUIGU.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqlsoftware.ATGUIGU.dao.iAccountMapper;
import com.lqlsoftware.ATGUIGU.dao.iBookMapper;
import com.lqlsoftware.ATGUIGU.dao.iTradeItemMapper;
import com.lqlsoftware.ATGUIGU.dao.iTradeMapper;
import com.lqlsoftware.ATGUIGU.entity.*;
import com.lqlsoftware.ATGUIGU.utils.Cart;
import com.lqlsoftware.ATGUIGU.utils.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 
 * 用户主体Controller
 * 
 * @author Robin Lu
 * 
 */

@Controller
public class AccountController {

    @Autowired
    private iBookMapper bookMapper;

    @Autowired
    private iAccountMapper accountMapper;

    @Autowired
    private iTradeMapper tradeMapper;

    @Autowired
    private iTradeItemMapper tradeItemMapper;

    // 付款
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void pay(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // 参数
        String name = request.getParameter("name");
        String cardId = request.getParameter("cardId");

        // 返回的JSON
        JSONObject msg = new JSONObject();
        JSONObject data = new JSONObject();

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            msg.put("code", -1);
            msg.put("errMsg", "登陆状态失效");
            response.getWriter().write(msg.toString());
            return;
        }

        if (name == null || name.equals("")) {
            msg.put("code", -1);
            msg.put("errMsg", "信用卡姓名输入有误");
            response.getWriter().write(msg.toString());
            return;
        }

        if (!user.getUser_name().equals(name)) {
            msg.put("code", -1);
            msg.put("errMsg", "信用卡姓名错误");
            response.getWriter().write(msg.toString());
            return;
        }

        if (cardId == null || cardId.equals("")
                || !cardId.equals(user.getAccount_id().toString())) {
            msg.put("code", -1);
            msg.put("errMsg", "信用卡账号输入有误");
            response.getWriter().write(msg.toString());
            return;
        }

        try {
            Integer.valueOf(cardId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            msg.put("code", -1);
            msg.put("errMsg", "信用卡账号输入有误");
            response.getWriter().write(msg.toString());
            return;
        }

        Account account = accountMapper.getAccountById(cardId);

        if (account == null) {
            msg.put("code", -1);
            msg.put("errMsg", "信用卡卡号错误");
            response.getWriter().write(msg.toString());
            return;
        }

        // 扣款
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            msg.put("code", -1);
            msg.put("errMsg", "购物车里是空的");
            response.getWriter().write(msg.toString());
            return;
        }


        Double total = cart.getTotalMoney();
        if (account.getBalance() < total) {
            msg.put("code", -1);
            msg.put("errMsg", "余额不足");
            response.getWriter().write(msg.toString());
            return;
        }

        for (CartItem values : cart.getBooks().values()) {
            if (values.getQuantity() > bookMapper.getBookById(values.getBook().getId().toString()).getStore_number()) {
                msg.put("code", -1);
                msg.put("errMsg", values.getBook().getTitle() + " 库存不足");
                response.getWriter().write(msg.toString());
                return;
            }
        }

        Trade trade = new Trade();
        trade.setTrade_time(new Date());
        trade.setUser_id(user.getUser_id());
        tradeMapper.insertTrade(trade);

        for (CartItem values : cart.getBooks().values()) {
            Book book = values.getBook();

            TradeItem tradeItem = new TradeItem();
            tradeItem.setBook_id(book.getId());
            tradeItem.setTrade_id(trade.getTrade_id());
            tradeItem.setQuantity(values.getQuantity().longValue());
            tradeItemMapper.insertTradeItem(tradeItem);

            book.setStore_number(book.getStore_number() - values.getQuantity());
            book.setSales_amount(book.getSales_amount() + values.getQuantity());
            bookMapper.updateBook(values.getBook());
        }

        account.setBalance(account.getBalance() - total);
        accountMapper.updateAccount(account);

        msg.put("code", 1);
        msg.put("errMsg", "付款成功");
        data.put("user", user.getUser_name());
        data.put("trade_time", trade.getTrade_time());
        data.put("trade_total", total);
        data.put("queryList", JSONArray.toJSON(cart.getList()));
        msg.put("data", data);
        response.getWriter().write(msg.toString());

        cart.clear();
        request.setAttribute("cart", cart);

        return;
    }
}