package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.SeatTypeEntity;
import com.etc.trainordersys.entity.TicketEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MyOrderMapper {
    //查询我的车票订单
    @Select("select * from t_order  where  user_id = #{userId}")
    @Results({
        @Result(column = "order_no",
                property = "tickets",
                javaType = List.class,
                many = @Many(select = "com.etc.trainordersys.mapper.MyOrderMapper.showMyTrainDetail"))
    })
    List<OrderEntity> showMyTrainOrder(Integer userId);
    //二次查询，查询车票订单详情
    @Select("select * from t_ticket where order_no = #{order_no}")
    @Results({
            @Result(column = "seat_type_id",
                    property = "seatType",
                    javaType = SeatTypeEntity.class,
                    one = @One(select = "com.etc.trainordersys.mapper.MyOrderMapper.showMySeatType"))
    })
    List<TicketEntity> showMyTrainDetail(String order_no);
    //三次查询，查询该车票的座位类型
    @Select("select * from t_seat_type where id = #{seat_type_id}")
    SeatTypeEntity showMySeatType(int seat_type_id);

}
