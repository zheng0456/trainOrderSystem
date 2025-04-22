package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TrainScheduleMapper {
    //查询火车时刻表信息   出发地:cityChoice  目的地:cityChoice1  出发时间：start
    @Select("select * from t_train_schedule where start_station=#{cityChoice} and end_station=#{cityChoice1} and start_time=#{start}")
    List<TrainScheduleEntity> findTrainSchedule(String cityChoice, String cityChoice1, String start);

    //查询火车的座位和票价
    @Select("select * from t_schedule_seat_info where train_number=#{trainName}")
    List<ScheduleSeatInfoEntity> findScheduleSeatInfo(String trainName);
}
