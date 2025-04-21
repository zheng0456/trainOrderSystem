package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TrainScheduleMapper {
    //查询火车票当天发行的所有票，价格，出发时间，到达时间
    @Select("select * from t_train_schedule where departure_date=#{goTime} and start_station=#{startStation} and end_station=#{endStation}")
    List<TrainScheduleEntity> findSelectTicket(String goTime, String startStation, String endStation);

    //查询火车票的价格
    @Select("select * from t_train_schedule s,t_schedule_seat_info i where i.train_number=s.train_number and departure_date=#{goTime} and start_station=#{startStation} and end_station=#{endStation}")
    List<ScheduleSeatInfoEntity> findSelectTicketPrices(String goTime, String startStation, String endStation);
}
