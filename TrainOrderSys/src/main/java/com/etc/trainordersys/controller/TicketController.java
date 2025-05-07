package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.entity.UserEntity;
import com.etc.trainordersys.service.ITicketService;
import com.etc.trainordersys.utils.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class TicketController {
    @Autowired
    @Qualifier("ticketService")
    ITicketService ticketService;

    //预定票判断预定前是否 在本账号下是否有订单存在未支付
    @GetMapping("/view/ticketing/findUnPayOrder")
    public @ResponseBody boolean findUnPayOrder(HttpSession session){
        int userId=((UserEntity)session.getAttribute("user")).getUser_id();
        //查询订单信息看用户是否有订单未支付
        boolean res =ticketService.findUserPayOrder(userId);
        //如果有订单为支付返回fail  没有返回true
        return res;
    }

    @Autowired
    private RedisUtils redisUtils;   //引入工具类
    //预定票 预定票点击的火车车次信息
    @RequestMapping("/view/ticketing/confirmPassenger")
    public String confirmPassenger( String trainInfo,Model model){
        TrainScheduleEntity train=redisUtils.stringToBean(trainInfo,TrainScheduleEntity.class);
        model.addAttribute("trainInfos",train);  //保存数据到model中
        return "/home/order/choose_passenger";
    }
}
