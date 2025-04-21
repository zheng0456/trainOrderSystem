package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainCarriageEntity {
    private int id;
    private int carriage_number;
    private int train_code;
    private int carriage_type;
    private int seat_nums;
    private int emp_seats;
    private String create_time;
    private String update_time;

}
