package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.TicketEntity;
import com.etc.trainordersys.entity.UserEntity;
import com.etc.trainordersys.service.IMyOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 我的订单
 */
@Controller
public class MyOrderController {
    @Autowired
    @Qualifier("myOrderService")
    IMyOrderService myOrderService;

    //显示订单中心----显示车票订单页面
    @RequestMapping("/system/order/train_order")
    public String showMyTrainOrder(HttpSession session, Model model,
                                   @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        //1.方法入参
        //2.每页展示的条数
        int pageSize=2;
        //3.设置起始页和每页展示的条数
        PageHelper.startPage(currentPage,pageSize);
        //4.分页查询我的车票订单,如果有搜索条件就模糊查询，没有就分页查询所有订单列表
        List<OrderEntity> orders = myOrderService.showMyTrainOrder(user_id);
        //5.封装结果集
        PageInfo<OrderEntity> pageInfo = new PageInfo<>(orders);
        //6.把数据保存到model中
        model.addAttribute("page",pageInfo);
        return "/home/center/order/train_order";
    }
}
