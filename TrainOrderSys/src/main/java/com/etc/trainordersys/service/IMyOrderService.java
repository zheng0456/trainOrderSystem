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
    //1.根据order_no,departure_date将t_order表中的order_status的值改为0，0代表已取消
    boolean updateOrder(String orderNo);
    //2.1根据order_no,departure_date查询t_ticket表
    List<TicketEntity> showMyTrainDetail(String orderNo);
    //2.2 循环修改t_ticket表中的ticket_status为6，6--已取消
    boolean updateTicketStatus(int ticketId, int ticketStatus);
    //3.根据train_number,seat_type_id将t_schedule_seat_info表中的remain_nums+1
    boolean updateTrainSeatNum(String trainNumber, int seatTypeId);
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
