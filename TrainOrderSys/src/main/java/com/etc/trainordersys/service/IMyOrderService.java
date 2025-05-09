package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.TicketEntity;

import java.util.List;

public interface IMyOrderService {
    //查询我的车票订单
    List<OrderEntity> showMyTrainOrder(Integer userId);
    //退票
    boolean refundTicket(Integer ticketId);
    //查询未支付订单
    OrderEntity showMyUnpaidOrder(Integer userId);
    //1.根据order_no,departure_date将t_order表中的order_status的值改为0，0代表已取消
    boolean updateOrder(Integer orderNo, String departureDate);
    //2.1根据order_no,departure_date查询t_ticket表
    List<TicketEntity> showMyTrainDetailByDate(Integer orderNo, String departureDate);
    //2.2 循环修改t_ticket表中的ticket_status为6，6--已取消
    boolean updateTicketStatus(int ticketId,String departureDate);
    //3.根据train_number,seat_type_id将t_schedule_seat_info表中的remain_nums+1
    boolean updateTrainSeatNum(String trainNumber, int seatTypeId);


}
