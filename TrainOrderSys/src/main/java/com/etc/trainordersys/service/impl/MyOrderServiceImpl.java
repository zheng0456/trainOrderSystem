package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.TicketEntity;
import com.etc.trainordersys.mapper.MyOrderMapper;
import com.etc.trainordersys.service.IMyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myOrderService")
public class MyOrderServiceImpl implements IMyOrderService {
    @Autowired
    private MyOrderMapper myOrderMapper;
    //查询我的车票订单
    @Override
    public List<OrderEntity> showMyTrainOrder(Integer userId) {
        return myOrderMapper.showMyTrainOrder(userId);
    }
    //退票
    @Override
    public boolean refundTicket(Integer ticketId) {
        return myOrderMapper.refundTicket(ticketId);
    }
    //查询未支付订单
    @Override
    public OrderEntity showMyUnpaidOrder(Integer userId) {
        return myOrderMapper.showMyUnpaidOrder(userId);
    }
    //1.根据order_no,departure_date将t_order表中的order_status的值改为0，0代表已取消
    @Override
    public boolean updateOrder(Integer orderNo, String departureDate) {
        return myOrderMapper.updateOrder(orderNo,departureDate);
    }
    //2.1根据order_no,departure_date查询t_ticket表
    @Override
    public List<TicketEntity> showMyTrainDetailByDate(Integer orderNo, String departureDate) {
        return myOrderMapper.showMyTrainDetailByDate(orderNo,departureDate);
    }
    //2.2 循环修改t_ticket表中的ticket_status为6，6--已取消
    @Override
    public boolean updateTicketStatus(int ticketId,String departureDate) {
        return myOrderMapper.updateTicketStatus(ticketId,departureDate);
    }
    //3.根据train_number,seat_type_id将t_schedule_seat_info表中的remain_nums+1
    @Override
    public boolean updateTrainSeatNum(String trainNumber, int seatTypeId) {
        return myOrderMapper.updateTrainNum(trainNumber,seatTypeId);
    }
}
