package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TicketMapper {
    //查询订单信息看用户是否有订单未支付
    @Select("select * from t_order where user_id=#{userId}")
    List<OrderEntity> findUserPayOrder(int userId);

    //查询该用户下的乘车人信息
    @Select("select * from t_passenger where userId=#{userId}")
    List<PassengerEntity> findUserPasseragesInformation(int userId);

    //查询火车列表
    @Select("select * from t_train_schedule where id=#{id}")
    TrainScheduleEntity findTrainInfoList(Integer id);

    //再次查询火车座位列表
    @Select("select * from t_schedule_seat_info where train_number=#{train_number}")
    @Results({
            //保存座位类型id数据
            @Result(column = "seat_type_id",property = "seat_type_id"),
            //查询座位类型名称
            @Result(column = "seat_type_id",
                    property = "seatType",
                    javaType = SeatTypeEntity.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.TicketMapper.findSeatTypeNames"))
    })
    List<ScheduleSeatInfoEntity> findSeatTypeList(String train_number);

    //再次查询火车座位类型
    @Select("select * from t_seat_type where id=#{seat_type_id}")
    SeatTypeEntity findSeatTypeNames(int seat_type_id);

    //再次查询火车车票信息
    @Select("select * from t_ticket where train_number=#{train_number}")
    @Results({
            //保存座位类型id数据
            @Result(column = "seat_type_id",property = "seat_type_id"),
            //查询座位类型名称
            @Result(column = "seat_type_id",
                    property = "seatType",
                    javaType = SeatTypeEntity.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.TicketMapper.findSeatTypeNames"))
    })
    List<TicketEntity> findTickets(String train_number);

    //根据火车名和火车开始日期，查询火车抢票详情
    @Select("select * from t_train_schedule t left join t_schedule_seat_info s on t.train_number=s.train_number where t.train_number=#{trainName} and t.departure_date=#{departureDate} and TIMESTAMPDIFF(SECOND, departure_time, arrival_time) != 0")
    TrainOrderEntity findSeckillDetail(String trainName, String departureDate);
}
