package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationEntity {
    private int station_id;
    private String station_name;
    private String city;
    private int railwayBureau_id;
    private String phone;
    private String create_time;
    private String update_time;
    private int del_flag;

}
