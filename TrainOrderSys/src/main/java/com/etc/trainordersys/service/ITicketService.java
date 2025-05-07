package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.OrderEntity;


import java.util.List;

public interface ITicketService {
    //查询订单信息看用户是否有订单未支付
    boolean findUserPayOrder(int userId);
}
