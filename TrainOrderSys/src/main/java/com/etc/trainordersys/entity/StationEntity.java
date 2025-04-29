package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//车站实体类
public class StationEntity {
    private int station_id;
    private String station_name;   //车站名
    private String city;    //城市名
    private int railwayBureau_id;   //铁路局id
    private String phone;
    private String create_time;
    private String update_time;
    private int del_flag;   //是否删除标志  1-还存在  0-不存在

}
