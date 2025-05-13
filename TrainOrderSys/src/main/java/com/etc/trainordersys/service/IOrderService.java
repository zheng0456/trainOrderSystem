package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.TrainOrderEntity;

public interface IOrderService {


    //查询用户订单下的买了几张票
    int ticketQuantitys(int userId, String trainNumber, String departureDate);

    //查询用户购买的票是那个火车，那个日期，那个座位类型的id
    int selectSeatListId(String trainNumber, int[] seatTypeId);

    //减库存，下订单（查询数据到订单表和订单详情表中）
    void doSeckill(TrainOrderEntity train);
}
