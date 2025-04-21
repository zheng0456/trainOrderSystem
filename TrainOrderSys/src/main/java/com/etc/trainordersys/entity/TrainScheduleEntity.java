package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
