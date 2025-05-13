package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 火车订单实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainOrderEntity {
    private String orderNum;   //订单编号
    private String trainName;
    private String departure_date;
    private int userId;
    private int [] seat_type_id;
    private int remain_nums;    //剩余票数

    private SeatInfoDTO seatInfoDTO;   //用户选择座位信息实体类
    private TrainScheduleEntity trainSchedule;   //用户选择的火车信息
}
