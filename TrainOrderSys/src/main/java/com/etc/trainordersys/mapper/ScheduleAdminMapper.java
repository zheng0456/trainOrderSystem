package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.StationEntity;
import com.etc.trainordersys.entity.TrainCarriageEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ScheduleAdminMapper {
    //显示车站列表
    @Select("select * from t_train_schedule")
    List<TrainScheduleEntity> findAllschedulePageList();
    @Select("select * from t_train_carriage")
    List<TrainCarriageEntity> findAllCarriage();
    @Select("select * from t_station")
    List<StationEntity> findAllStationPageList();
    //保存添加车次
    @Insert("insert into t_train_schedule(train_code,train_number,start_station,end_station,departure_date,departure_time,arrive_time,create_time,update_time) values(#{train_code},#{train_number},#{start_station},#{end_station},#{departure_date},#{departure_time},#{arrive_time},now(),now())")
    int addSchedule(TrainScheduleEntity trainSchedule);
    //显示编辑车次页面
    @Select("select * from t_train_schedule where id=#{id}")
    TrainScheduleEntity findAllscheduleById(int id);
    //保存编辑车次信息
    @Update("update t_train_schedule set train_code=#{train_code},train_number=#{train_number},start_station=#{start_station},end_station=#{end_station},departure_date=#{departure_date},departure_time=#{departure_time},arrive_time=#{arrive_time},start_time=#{start_time},end_time=#{end_time},update_time=now() " +
            "where id=#{id}")
    int edit(TrainScheduleEntity trainSchedule);
}
