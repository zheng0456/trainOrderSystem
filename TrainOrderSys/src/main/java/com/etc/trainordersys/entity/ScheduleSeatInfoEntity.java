package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//座位信息表
public class ScheduleSeatInfoEntity {
    private int id;
    private String train_number;   //火车名
    private int seat_type_id;     //座位类型号
    private int remain_nums;  //等于0 没有余票  ，不等于0还有余票  ，剩余票数
    private double price;

    private SeatTypeEntity seatType;  //座位类型
}
