package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//座位信息表
public class ScheduleSeatInfoEntity {
    private int id;
    private String train_number;
    private int seat_type_id;
    private int remain_nums;
    private double price;
}
