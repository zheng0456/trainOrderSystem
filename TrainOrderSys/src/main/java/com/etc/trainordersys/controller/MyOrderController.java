package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.*;
import com.etc.trainordersys.service.IMyOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<OrderEntity> allOrders = myOrderService.showMyTrainOrder(user_id);
        //将已支付订单方法新的数组中
        List<OrderEntity> orders = new ArrayList<>();
        for (OrderEntity order:allOrders){
            if (order.getPayment()==1 && order.getOrder_status()==1){
                orders.add(order);
            }
        }
        //5.封装结果集
        PageInfo<OrderEntity> pageInfo = new PageInfo<>(orders);
        //6.把数据保存到model中
        model.addAttribute("page",pageInfo);
        return "/home/center/order/train_order";
    }
    //退票
    @PutMapping("/system/order/refundTicket")
    public @ResponseBody ResponseResult refundTicket(@RequestParam(value = "ticket_id") Integer ticket_id){
        boolean isSuccess = myOrderService.refundTicket(ticket_id);
        if (isSuccess){
            return ResponseResult.success(ResultEnum.SUCCESS);
        }else {
            return ResponseResult.fail(ResultEnum.FAIL);
        }
    }
    //查询并跳转未查询订单页面
    @GetMapping("/system/order/unpaidOrder")
    public String showUnpaidOrder(HttpSession session,Model model){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        //查询未支付订单
        OrderEntity unPaidOrder = myOrderService.showMyUnpaidOrder(user_id);
        model.addAttribute("order",unPaidOrder);
        return "/home/center/order/unpaid_order";
    }
    //用户取消订单
    @Transactional
    @RequestMapping("/system/order/cancelOrder")
    public @ResponseBody boolean cancelOrder(@RequestParam(value = "order_no") Integer order_no,
                                            @RequestParam(value = "train_number") String train_number,
                                            @RequestParam(value = "departure_date") String departure_date){
        //1.根据order_no,departure_date将t_order表中的order_status的值改为0，0代表已取消
        boolean updateOrder = myOrderService.updateOrder(order_no,departure_date);
        if (updateOrder){
            //2.1根据order_no,departure_date查询t_ticket表
            List<TicketEntity> tickets = myOrderService.showMyTrainDetailByDate(order_no,departure_date);
            for (TicketEntity ticket:tickets){
                //2.2 循环修改t_ticket表中的ticket_status为6，6--已取消
                boolean updateTicketStatus = myOrderService.updateTicketStatus(ticket.getTicket_id(),departure_date);
                if (updateTicketStatus){
                    //3.根据train_number,seat_type_id将t_schedule_seat_info表中的remain_nums+1
                    myOrderService.updateTrainSeatNum(train_number,ticket.getSeat_type_id());
                }
            }
            return true;
        }
        return false;
    }

    //查询历史订单
    @RequestMapping("/system/order/history_order")
    public String showMyHistoryTrainOrder(HttpSession session, Model model,
                                   @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        //1.方法入参
        //2.每页展示的条数
        int pageSize=2;
        //3.设置起始页和每页展示的条数
        PageHelper.startPage(currentPage,pageSize);
        //4.分页查询我的车票订单,如果有搜索条件就模糊查询，没有就分页查询所有订单列表
        List<OrderEntity> allOrders = myOrderService.showMyTrainOrder(user_id);
        //将不是默认状态订单方法新的数组中(0未支付，1订单默认）
        List<OrderEntity> orders = new ArrayList<>();
        for (OrderEntity order:allOrders){
            if (!(order.getPayment()==0 && order.getOrder_status()==1)){
                orders.add(order);
            }
        }
        //5.封装结果集
        PageInfo<OrderEntity> pageInfo = new PageInfo<>(orders);
        //6.把数据保存到model中
        model.addAttribute("page",pageInfo);
        return "/home/center/order/history_order";
    }
}
