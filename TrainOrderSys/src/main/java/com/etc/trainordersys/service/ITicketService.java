package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.PassengerEntity;
import com.etc.trainordersys.entity.TicketEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;


import java.util.List;

public interface ITicketService {
    //查询订单信息看用户是否有订单未支付
    boolean findUserPayOrder(int userId);

    //查询该用户下的乘车人信息
    List<PassengerEntity> findUserPasseragesInformation(int userId);

    //添加订票信息
    int addTickets(String departureDate, String departureTime, String trainNumber, String startStation, String endStation, String arriveTime, Integer[] sort, Integer [] ticketType, String[] seatType, String[] passengerName, String[] cardType, String[] cardCode);

    //判断用户选的座位类型
    int findFlagSeat(Integer[] sort, String[] seatType);

    //查询火车列表
    TrainScheduleEntity findTrainInfoList(Integer id);

    //创建临时缓存添加乘客数据
    List<TicketEntity> TemporaryAddTickets(Integer[] sort, Integer[] ticketType, String[] seatType, String[] passengerName, String[] cardType, String[] cardCode);
}
