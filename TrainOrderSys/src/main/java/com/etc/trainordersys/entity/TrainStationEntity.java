package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//火车车站实体类
public class TrainStationEntity {
    private int id;
    private String train_number;
    private int sort;
    private String arrive_time;
    private String start_time;
    private double kilometers;
    private int station_id;
    private String create_time;
    private String update_time;

}
