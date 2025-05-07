package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//座位实体类
public class SeatEntity {
    private int id;
    private String line;   //座位列 A B C
    private int number;//号：1，2，3
    private String train_number;  // 火车名
    private int carriage_number;  //车厢号
    private int seat_type;  //座位类型
    private int seat_status;   //座位状态  1-有人    2-没人
    private String create_time;
    private String update_time;

}
