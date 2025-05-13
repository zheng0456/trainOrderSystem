package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
public interface OrderMapper {

    //插入用户订单信息
    @Insert("insert into t_order values(#{order_no},#{train_number},#{departure_date},null,null,0,#{user_id},null,now(),now())")
    boolean insertOrderInfo(OrderEntity order);

    //插入用户购票信息
    @Insert("insert into t_ticket values(null,#{orderNum},#{train_number},#{startStation},#{endStation},#{departureTime},#{arriveTime},#{departureDate},#{passengerName},#{cardType},#{cardCode},#{ticketType},#{seatTypeId},null,null,#{ticketPrice},null,now(),now(),1)")
    void insertTicksInfo(String orderNum, String trainNumber, String startStation, String endStation, String departureTime, String arriveTime, String departureDate, String passengerName,String cardType,String cardCode, String ticketType,int seatTypeId,double ticketPrice);

    //插入用户购票数量
    @Update("update t_order set ticket_quantity=#{length} where order_no=#{orderNum}")
    void updateTicketQuantity(int length, String orderNum);

    //查询用户订单下的买了几张票
    @Select("select ticket_quantity from t_order where train_number=#{trainNumber} and departure_date=#{departureDate} and user_id=#{userId}")
    int ticketQuantitys(int userId, String trainNumber, String departureDate);

    //查询用户购买的票是那个火车，那个日期，那个座位类型的id
    @Select("select id from t_schedule_seat_info where train_number=#{trainNumber} and seat_type_id=#{seatTypeId}")
    int selectSeatListId(String trainNumber, int[] seatTypeId);

    //循环遍历更新票的库存
    @Update("update t_schedule_seat_info set remain_nums=remain_nums-1 where train_number=#{trainName} and seat_type_id=#{i}")
    void updateRemainNums(String trainName, int i);
}
