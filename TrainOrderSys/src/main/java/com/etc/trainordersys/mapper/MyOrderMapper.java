package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.SeatTypeEntity;
import com.etc.trainordersys.entity.TicketEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MyOrderMapper {
    //查询我预定的所有订单
    @Select("<script>" +
            "select * from t_order " +
            "<where>" +
            " user_id = #{userId} " +
            "<if test='orderNo != null '> AND order_no like concat('%', #{orderNo}, '%')</if>" +
            "<if test='startDate != null and endDate != null and !startDate.isEmpty() and !endDate.isEmpty()'> AND create_time BETWEEN #{startDate} and #{endDate}</if>" +
            "</where>" +
            "</script>")
    @Results({
        @Result(column = "order_no",property = "order_no"),
        @Result(column = "order_no",
                property = "tickets",
                javaType = List.class,
                many = @Many(select = "com.etc.trainordersys.mapper.MyOrderMapper.showMyTrainDetail"))
    })
    List<OrderEntity> showMyTrainOrder(Integer userId,String orderNo,String startDate,String endDate);
    //二次查询，查询车票订单详情
    @Select("select * from t_ticket where order_no = #{order_no}")
    @Results({
            @Result(column = "seat_type_id",property = "seat_type_id"),
            @Result(column = "seat_type_id",
                    property = "seatType",
                    javaType = SeatTypeEntity.class,
                    one = @One(select = "com.etc.trainordersys.mapper.MyOrderMapper.showMySeatType"))
    })
    List<TicketEntity> showMyTrainDetail(String order_no);
    //三次查询，查询该车票的座位类型
    @Select("select * from t_seat_type where id = #{seat_type_id}")
    SeatTypeEntity showMySeatType(int seat_type_id);
    //退票
    @Update("update t_ticket set update_time = now(), ticket_status = 7 where ticket_Id = #{ticketId}")
    boolean refundTicket(Integer ticketId);

    //查询我预定的未支付订单
    @Select("select * from t_order  where  user_id = #{userId} and order_status = 1 and payment = 0")
    @Results({
            @Result(column = "order_no",property = "order_no"),
            @Result(column = "order_no",
                    property = "tickets",
                    javaType = List.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.MyOrderMapper.showMyTrainDetail"))
    })
    OrderEntity showMyUnpaidOrder(Integer userId);

    //支付成功后，支付状态
    @Update("update t_order set payment = 1 where order_no=#{outTradeNo}")
    boolean updateOrderStatus(String outTradeNo);
    //查询我的车票订单详情
    @Select("select * from t_ticket where card_Code=#{cardCode}")
    @Results({
            @Result(column = "seat_type_id",property = "seat_type_id"),
            @Result(column = "seat_type_id",
                    property = "seatType",
                    javaType = SeatTypeEntity.class,
                    one = @One(select = "com.etc.trainordersys.mapper.MyOrderMapper.showMySeatType"))
    })
    List<TicketEntity> showMyTickets(String cardCode);
    //查询退票信息
    @Select("select * from t_order  where  user_id = #{userId}")
    @Results({
            @Result(column = "order_no",property = "order_no"),
            @Result(column = "order_no",
                    property = "tickets",
                    javaType = List.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.MyOrderMapper.showMyTrainDetail"))
    })
    List<OrderEntity> showRefundTrainOrder(Integer userId);
    //1.根据order_no,departure_date将t_order表中的order_status的值改为0，0代表已取消
    @Update("update t_order set order_status = 0 where order_no=#{orderNo}")
    boolean updateOrder(String orderNo);

    // 循环修改t_ticket表中的ticket_status为6，6--已取消
    @Update("update t_ticket set ticket_status = #{ticketStatus},update_time = now() where ticket_id = #{ticketId}")
    boolean updateTicketStatus(int ticketId, int ticketStatus);
    //根据train_number,seat_type_id将t_schedule_seat_info表中的remain_nums+1
    @Update("update t_schedule_seat_info set remain_nums = remain_nums+1 where train_number=#{trainNumber} and seat_type_id =#{seatTypeId}")
    boolean updateTrainNum(String trainNumber, int seatTypeId);
    //3.根据trainNumber和departureDate查询t_train_schedule表的train_code
    @Select("select train_code from t_train_schedule where train_number=#{trainNumber} and departure_date=#{departureDate}")
    Integer selectTrainCode(String trainNumber, String departureDate);
}
