package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.mapper.TicketMapper;
import com.etc.trainordersys.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ticketService")
public class TicketServiceImpl implements ITicketService {
    @Autowired
    private TicketMapper ticketMapper;

    //查询订单信息看用户是否有订单未支付
    @Override
    public boolean findUserPayOrder(int userId) {
        List<OrderEntity> orderEntity=ticketMapper.findUserPayOrder(userId);
        //如果返回值为0 已支付  返回为1 未支付
        for (int i=0;i<orderEntity.size();i++){
            if (orderEntity.get(i).getPayment()==0){
                return true;
            }
        }
        return false;
    }
}
