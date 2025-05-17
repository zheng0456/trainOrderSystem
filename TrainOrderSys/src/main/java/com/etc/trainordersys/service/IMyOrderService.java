package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.TicketEntity;

import java.util.List;

public interface IMyOrderService {
    //查询我的车票订单
    List<OrderEntity> showMyTrainOrder(Integer userId,String orderNo,String startDate,String endDate);
    //退票
    boolean refundTicket(Integer ticketId);
    //查询未支付订单
    OrderEntity showMyUnpaidOrder(Integer userId);
    //支付成功回调方法
    boolean successPay(String outTradeNo);
    //查询我的车票订单详情
    List<TicketEntity> showMyTickets(String cardCode);
    //查询历史订单
    List<OrderEntity> showMyHistoryTrainOrder(Integer userId,String orderNo,String startDate,String endDate);
    //查询退票订单
    List<TicketEntity> showRefundTickets(Integer userId);
    //用户取消订单
    boolean cancelOrder(String orderNo, String trainNumber, String departureDate);
}
