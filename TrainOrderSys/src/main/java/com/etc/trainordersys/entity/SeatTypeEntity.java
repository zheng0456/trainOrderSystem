package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//座位类型
public class SeatTypeEntity {
    private int id;
    private String seat_type_name;   //座位类型名字
    private String remark;     //备注
    private String create_time;
    private String update_time;
}
