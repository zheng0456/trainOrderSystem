package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.*;


import java.util.List;

public interface ITicketService {
    //查询订单信息看用户是否有订单未支付
    boolean findUserPayOrder(int userId);

    //查询该用户下的乘车人信息
    List<PassengerEntity> findUserPasseragesInformation(int userId);

    //判断用户选的座位类型
    int findFlagSeat(Integer[] sort, String[] seatType);

    //查询火车列表
    TrainScheduleEntity findTrainInfoList(Integer id);

    //创建临时缓存添加乘客数据
    List<TicketEntity> TemporaryAddTickets(Integer[] sort, Integer[] ticketType, String[] seatType, String[] passengerName, String[] cardType, String[] cardCode);

    //根据火车名和火车开始日期，查询火车抢票详情
    TrainOrderEntity findSeckillDetail(String trainName, String departureDate);
}
