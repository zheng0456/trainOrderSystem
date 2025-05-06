package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TicketMapper {
    //查询订单信息看用户是否有订单未支付
    @Select("select * from t_order where user_id=#{userId}")
    List<OrderEntity> findUserPayOrder(int userId);
}
