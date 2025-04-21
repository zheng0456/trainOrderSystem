package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatTypeEntity {
    private int id;
    private String seat_type_name;
    private String remark;
    private String create_time;
    private String update_time;
}
