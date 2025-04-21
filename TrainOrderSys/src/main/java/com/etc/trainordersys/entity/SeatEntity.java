package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatEntity {
    private int id;
    private String line;
    private int number;//号：1，2，3
    private String train_number;
    private int carriage_number;
    private int seat_type;
    private int seat_status;
    private String create_time;
    private String update_time;
}
