package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.TicketEntity;
import com.etc.trainordersys.mapper.MyOrderMapper;
import com.etc.trainordersys.service.IMyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("myOrderService")
public class MyOrderServiceImpl implements IMyOrderService {
    @Autowired
    private MyOrderMapper myOrderMapper;

    //查询我的车票订单
    @Override
    public List<OrderEntity> showMyTrainOrder(Integer userId, String orderNo, String startDate, String endDate) {
        List<OrderEntity> allOrders = myOrderMapper.showMyTrainOrder(userId, orderNo, startDate, endDate);
        //将已支付订单方法放到新的数组中
        List<OrderEntity> orders = new ArrayList<>();
        List<OrderEntity> nullOrders = new ArrayList<>();
        for (OrderEntity order : allOrders) {//订单已支付，状态正常
            if (order.getPayment() == 1 && order.getOrder_status() == 1) {
                List<TicketEntity> tickets = new ArrayList<>();
                for (TicketEntity ticket : order.getTickets()) {
                    //车票状态未使用或已改签
                    if (ticket.getTicket_status() == 0 || ticket.getTicket_status() == 5 || ticket.getTicket_status() == 7) {
                        tickets.add(ticket);
                    } else {
                        return nullOrders;
                    }
                }
                order.setTickets(tickets);
                orders.add(order);
            }
        }
        return orders;
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

    //支付成功后，修改订单状态和支付状态
    @Override
    @Transactional
    public boolean successPay(String outTradeNo) {
        //1.支付成功后，修改支付状态
        boolean res = myOrderMapper.updateOrderStatus(outTradeNo);
        if (res) {
            //2.查询该订单下的车票详情
            List<TicketEntity> tickets = myOrderMapper.showMyTrainDetail(outTradeNo);
            for (TicketEntity ticket : tickets) {
                //3.循环修改该订单下的车票详情的车票状态为未使用
                ticket.setTicket_status(0);
                myOrderMapper.updateTicketStatus(ticket.getTicket_id(), ticket.getTicket_status());
            }
        }
        return true;
    }

    //查询我的车票订单详情
    @Override
    public List<TicketEntity> showMyTickets(String cardCode) {
        return myOrderMapper.showMyTickets(cardCode);
    }

    //查询历史订单
    @Override
    public List<OrderEntity> showMyHistoryTrainOrder(Integer userId, String orderNo, String startDate, String endDate) {
        List<OrderEntity> allOrders = myOrderMapper.showMyTrainOrder(userId, orderNo, startDate, endDate);
        //将不是默认状态订单方法新的数组中(0未支付，1订单默认）
        List<OrderEntity> orders = new ArrayList<>();
        for (OrderEntity order : allOrders) {
            if (!(order.getPayment() == 0 && order.getOrder_status() == 1)) {
                orders.add(order);
            }
        }
        return orders;
    }

    //查询退票订单
    @Override
    public List<TicketEntity> showRefundTickets(Integer userId) {
        //查询本账号所有的订单
        List<OrderEntity> allOrders = myOrderMapper.showRefundTrainOrder(userId);
        //将查到的本人订单中的改签中的车票状态添加到新列表中
        //4.分页查询退款中的车票订单详情,如果有搜索条件就模糊查询，没有就分页查询所有订单列表
        List<TicketEntity> refundTickets = new ArrayList<>();
        for (OrderEntity order : allOrders) {//订单已支付，状态正常
            if (order.getPayment() == 1 && order.getOrder_status() == 1) {
                for (TicketEntity ticket : order.getTickets()) {
                    //车票状态为改签中
                    if (ticket.getTicket_status() == 7) {
                        refundTickets.add(ticket);
                    }
                }
            }
        }
        return refundTickets;
    }

    //用户取消订单
    @Override
    @Transactional
    public boolean cancelOrder(String orderNo, String trainNumber, String departureDate) {
        //1.根据order_no将t_order表中的order_status的值改为0，0代表已取消
        boolean updateOrder = myOrderMapper.updateOrder(orderNo);
        if (updateOrder) {
            //2.根据order_no查询t_ticket表
            List<TicketEntity> tickets = myOrderMapper.showMyTrainDetail(orderNo);
            //3.根据trainNumber和departureDate查询t_train_schedule表的train_code
            Integer trainCode = myOrderMapper.selectTrainCode(trainNumber, departureDate);
            for (TicketEntity ticket : tickets) {
                //4.根据trainCode和ticket.carriage_number查询t_train_carriage表的carriage_type
                Integer carriageType = myOrderMapper.selectTrainCarriage(trainCode, ticket.getCarriage_number());
                //5.如果carriageType与ticket.seat_type_id不一样说明买的站票
                if (carriageType != ticket.getSeat_type_id()) {
                    //5.1无座数加1
                    myOrderMapper.updateEmpSeat(trainCode, ticket.getCarriage_number());
                } else {
                    //5.2相应座位类型数加1
                    myOrderMapper.updateIsSeat(trainCode, ticket.getCarriage_number());
                }
                //6 循环修改t_ticket表中的ticket_status为6，6--已取消
                ticket.setTicket_status(6);
                boolean updateTicketStatus = myOrderMapper.updateTicketStatus(ticket.getTicket_id(), ticket.getTicket_status());
                if (updateTicketStatus) {
                    //7.根据train_number,seat_type_id将t_schedule_seat_info表中的remain_nums+1
                    boolean res = myOrderMapper.updateTrainSeatNum(trainNumber, ticket.getSeat_type_id());
                    //不是站票的情况下才走下方
                    if (res && ticket.getSeat_type_id() != 13) {
                        //如果是硬座那么ticket中的seat_number要转换为int类型,where条件与seat_number判断
                        if (ticket.getSeat_type_id() == 0) {
                            //8.有座情况根据seat_number，train_number和carriage_number修改t_seat表的座位状态
                            myOrderMapper.updateSeatStatus(Integer.parseInt(ticket.getSeat_number()), ticket.getCarriage_number(), ticket.getTrain_number());
                        } else {
                            //where条件与line判断
                            myOrderMapper.updateSeatStatus1(ticket.getSeat_number(), ticket.getCarriage_number(), ticket.getTrain_number());
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
