package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.OrderEntity;
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


}
