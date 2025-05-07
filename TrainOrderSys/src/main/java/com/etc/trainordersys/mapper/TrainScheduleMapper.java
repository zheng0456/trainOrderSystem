package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrainScheduleMapper {

    //出发地，开始时间，目的地，去查询火车时刻表 ：TrainScheduleEntity  CONCAT拼接字符串
    //三个表连接
    @Select("SELECT * FROM t_train_schedule WHERE end_station LIKE CONCAT('%', #{end_station_now} ,'%') AND start_station LIKE CONCAT('%', #{start_station_now} ,'%') AND departure_date=#{go_time}")
    @Results({
            //column:数据库列表，二次查询需要的数据
            //property:需要装配的属性名（实体类的属性名）
            //javaType:属性对应的实体类
            //many:需要使用的@Many注解（@Result(many=@many)()）多个数据

            @Result(column = "train_number",property = "train_number"),
            //再次查询火车总时间
            @Result(column = "train_code",
                    property = "totalTime",
                    javaType = String.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.TrainScheduleMapper.findTotalTimes")),
            //再次查询是否有火车
            @Result(column = "train_code",
                    property = "trainEntity",
                    javaType = TrainEntity.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.TrainScheduleMapper.findTrainStatus")),

            //再次查询火车座位信息
            @Result(column = "train_number",
                    property = "seatInfoEntityList",
                    javaType = List.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.TrainScheduleMapper.findSeatInfoEntityList"))

    })
    List<TrainScheduleEntity> findtrainScheduleList(String start_station_now,String end_station_now, String go_time);

    //再次查询 火车总时间
    @Select(" SELECT " +
            "    CONCAT(" +
            "        CAST(TIMESTAMPDIFF(HOUR, departure_time, arrive_time) AS CHAR), '时', " +
            "        CAST(TIMESTAMPDIFF(MINUTE, departure_time, arrive_time) % 60 AS CHAR), '分' " +
            "    ) AS formatted_time_diff " +
            "FROM t_train_schedule " +
            "WHERE train_code=#{train_code};")
    String findTotalTimes(String train_code);

    //查询是否有火车
    @Select("select * from t_train where train_code=#{train_code}")
    TrainEntity findTrainStatus(String train_code);

    //再次查询座位信息
    @Select("select * from t_schedule_seat_info where train_number=#{train_number}")
    @Results({
            @Result(column = "seat_type_id",property = "seat_type_id"),
            @Result(column = "seat_type_id",
                    property = "seatType",
                    javaType =SeatTypeEntity.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.TrainScheduleMapper.findSeatTypeList"))
    })
    List<ScheduleSeatInfoEntity> findSeatInfoEntityList(String train_number);
    //查询座位信息和票数
    @Select("select * from t_seat_type where id=#{seat_type_id}" )
    SeatTypeEntity findSeatTypeList(int seat_type_id);

}
