package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//火车时刻表
public class TrainScheduleEntity {
    private int id;
    private int train_code;
    private String train_number;
    private String start_station;
    private String end_station;
    private String departure_date;
    private String departure_time;
    private String arrive_time;
    private String start_time;
    private String end_time;
    private String create_time;
    private String update_time;
    private String totalTime;  //总时间

    private List<ScheduleSeatInfoEntity> seatInfoEntityList;  //座位信息表

    private List<SeatTypeEntity> seatType;   //火车作为类型

    private TrainEntity trainEntity;  //  火车实体类
    private List<TicketEntity> tickets;   //一列火车有多个票
}
