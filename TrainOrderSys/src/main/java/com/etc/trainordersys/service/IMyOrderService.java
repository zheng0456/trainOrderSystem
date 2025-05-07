package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.OrderEntity;

import java.util.List;

public interface IMyOrderService {
    //查询我的车票订单
    List<OrderEntity> showMyTrainOrder(Integer userId);
}
