package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 存放前端传来的订单信息 用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainInfoDTO {
    private String train_number;  //火车名字
    private String start_station; //火车开始站
    private String end_station;   //火车结束站
    private String departure_date;  //火车开始日期
    private String departure_time;  //火车开始时间
    private String arrive_time;     //火车到达时间
    private String start_time;     //抢票开始时间
    private String end_time;     //抢票结束时间
    private List<TicketEntity> tickets;   //票的实体类列表
}
