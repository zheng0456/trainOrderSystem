package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.mapper.OrderMapper;
import com.etc.trainordersys.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;


}
