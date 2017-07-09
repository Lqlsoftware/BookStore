package com.lqlsoftware.ATGUIGU.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqlsoftware.ATGUIGU.dao.iBookMapper;
import com.lqlsoftware.ATGUIGU.dao.iTradeItemMapper;
import com.lqlsoftware.ATGUIGU.dao.iTradeMapper;
import com.lqlsoftware.ATGUIGU.entity.*;
import com.lqlsoftware.ATGUIGU.utils.TradeInfo;
import com.lqlsoftware.ATGUIGU.utils.TradeItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 
 * 用户主体Controller
 * 
 * @author Robin Lu
 * 
 */

@Controller
public class TradeController {

    @Autowired
    private iTradeMapper tradeMapper;

    @Autowired
    private iTradeItemMapper tradeItemMapper;

    @Autowired
    private iBookMapper bookMapper;

    @RequestMapping(value = "/getBills", method = RequestMethod.GET)
    public void getBills(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 设置传输数据格式
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "xml;charset=UTF-8");

        // 返回json
        JSONObject msg = new JSONObject();
        JSONObject data = new JSONObject();

        // 获取登陆状态
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            msg.put("code", -2);
            msg.put("errMsg", "登陆状态失效");
            response.getWriter().write(msg.toString());
            return;
        }

        // 得到交易纪录
        Collection<Trade> trades = tradeMapper.getTradeByUserId(user.getUser_id().toString());

        ConcurrentLinkedDeque<TradeInfo> res = new ConcurrentLinkedDeque<>();

        for (Trade values : trades) {
            TradeInfo info = new TradeInfo(values.getTrade_time());

            Collection<TradeItem> c = tradeItemMapper.getTradeItemByTradeId(values.getTrade_id().toString());

            for (TradeItem item : c) {
                TradeItemInfo tradeItemInfo = new TradeItemInfo();
                Book book = bookMapper.getBookById(item.getBook_id().toString());
                tradeItemInfo.setTitle(book.getTitle());
                tradeItemInfo.setPrice(book.getPrice());
                tradeItemInfo.setQuantity(item.getQuantity());

                info.add(tradeItemInfo);
            }

            res.add(info);
        }

        msg.put("code", 1);
        msg.put("errMsg", "");
        data.put("user", user.getUser_name());
        data.put("trade", JSONArray.toJSON(res));
        msg.put("data", data);
        response.getWriter().write(msg.toString());
        return;

    }
}