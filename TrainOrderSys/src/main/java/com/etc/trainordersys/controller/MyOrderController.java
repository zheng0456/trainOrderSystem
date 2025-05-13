package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.*;
import com.etc.trainordersys.service.IMyOrderService;
import com.etc.trainordersys.utils.AlipayUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @RequestMapping("/view/order/train_order")
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
    //退票
    @PutMapping("/view/order/refundTicket")
    public @ResponseBody ResponseResult refundTicket(@RequestParam(value = "ticket_id") Integer ticket_id){
        boolean isSuccess = myOrderService.refundTicket(ticket_id);
        if (isSuccess){
            return ResponseResult.success(ResultEnum.SUCCESS);
        }else {
            return ResponseResult.fail(ResultEnum.FAIL);
        }
    }
    //查询并跳转未支付订单页面
    @GetMapping("/view/order/unpaidOrder")
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
    @RequestMapping("/view/order/cancelOrder")
    public @ResponseBody boolean cancelOrder(@RequestParam(value = "order_no") String order_no,
                                            @RequestParam(value = "train_number") String train_number,
                                            @RequestParam(value = "departure_date") String departure_date){
        //1.根据order_no将t_order表中的order_status的值改为0，0代表已取消
        boolean updateOrder = myOrderService.updateOrder(order_no);
        if (updateOrder){
            //2.1根据order_no查询t_ticket表
            List<TicketEntity> tickets = myOrderService.showMyTrainDetail(order_no);
            for (TicketEntity ticket:tickets){
                //2.2 循环修改t_ticket表中的ticket_status为6，6--已取消
                ticket.setTicket_status(6);
                boolean updateTicketStatus = myOrderService.updateTicketStatus(ticket.getTicket_id(),ticket.getTicket_status());
                if (updateTicketStatus){
                    //3.根据train_number,seat_type_id将t_schedule_seat_info表中的remain_nums+1
                    myOrderService.updateTrainSeatNum(train_number,ticket.getSeat_type_id());
                    //4.恢复
                }
            }
            return true;
        }
        return false;
    }

    //查询历史订单
    @RequestMapping("/view/order/history_order")
    public String showMyHistoryTrainOrder(HttpSession session, Model model,
                                   @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        //1.方法入参
        //2.每页展示的条数
        int pageSize=2;
        //3.设置起始页和每页展示的条数
        PageHelper.startPage(currentPage,pageSize);
        //4.分页查询历史车票订单,如果有搜索条件就模糊查询，没有就分页查询所有订单列表
        List<OrderEntity> historyOrders = myOrderService.showMyHistoryTrainOrder(user_id);
        //5.封装结果集
        PageInfo<OrderEntity> pageInfo = new PageInfo<>(historyOrders);
        //6.把数据保存到model中
        model.addAttribute("page",pageInfo);
        return "/home/center/order/history_order";
    }
    //支付订单
    @Autowired
    AlipayUtils alipayUtils;
    //支付订单
    @RequestMapping("/view/ticketing/toPay")
    public @ResponseBody String payOrder(String order_no,double total_price,String train_number,String departure_date){
        System.out.println(order_no);
        System.out.println(total_price);
        System.out.println(train_number);
        System.out.println(departure_date);
        String form = alipayUtils.pay(order_no,total_price,train_number);  //调用支付宝支付接口
        return form;
    }
    //支付成功的回调方法
    @RequestMapping("/return")
    public String returnPage(String out_trade_no){
        System.out.println("out_trade_no："+ out_trade_no);
        //1.支付成功后，修改支付状态
        boolean res = myOrderService.successPay(out_trade_no);
        if (res){
            return "forward:/view/order/unpaidOrder";
        }
        return "redirect:/error-page";
    }
    //查看本人车票
    @RequestMapping("/view/order/my_tickets")
    public String showMyTickets(HttpSession session, Model model,
                                @RequestParam(value = "currentPage",required = false,defaultValue = "1") Integer currentPage){
        //获取session中我的身份证号
        String card_code = ((UserEntity)session.getAttribute("user")).getCard_code();
        //1.方法入参
        //2.每页展示的条数
        int pageSize=2;
        //3.设置起始页和每页展示的条数
        PageHelper.startPage(currentPage,pageSize);
        //4.分页查询我的车票订单详情,如果有搜索条件就模糊查询，没有就分页查询所有订单列表
        List<TicketEntity> myTickets = myOrderService.showMyTickets(card_code);
        for (TicketEntity ticket:myTickets) {
            //根据发车日期转换成星期几
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ticketDate = LocalDate.parse(ticket.getDeparture_date(), formatter);
            // 获取星期几
            DayOfWeek dayOfWeek = ticketDate.getDayOfWeek();
            System.out.println("星期几: " + dayOfWeek); // 输出：WEDNESDAY
            // 中文输出
            String[] chineseDays = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
            System.out.println("中文星期几: " + chineseDays[dayOfWeek.getValue() - 1]); // 输出：星期三
            ticket.setDayOfWeek(chineseDays[dayOfWeek.getValue() - 1]);
        }
        //5.封装结果集
        PageInfo<TicketEntity> pageInfo = new PageInfo<>(myTickets);
        //6.把数据保存到model中
        model.addAttribute("page",pageInfo);
        return "/home/center/ticket/my_tickets";
    }
    //查询退票车票
    @RequestMapping("/view/order/findRefundTicket")
    public String showRefundTickets(HttpSession session, Model model,
                                    @RequestParam(value = "currentPage",required = false,defaultValue = "1") Integer currentPage){
        //获取session中我的身份证号
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        //1.方法入参
        //2.每页展示的条数
        int pageSize=2;
        //3.设置起始页和每页展示的条数
        PageHelper.startPage(currentPage,pageSize);
        //查询退票的订单
        List<TicketEntity> refundTickets = myOrderService.showRefundTickets(user_id);
        //5.封装结果集
        PageInfo<TicketEntity> pageInfo = new PageInfo<>(refundTickets);
        //6.把数据保存到model中
        model.addAttribute("page",pageInfo);
        return "/home/center/ticket/my_tickets";
    }
}
