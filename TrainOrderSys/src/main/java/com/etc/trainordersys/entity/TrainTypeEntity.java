package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//火车类型实体类
public class TrainTypeEntity {
    private int type_id;
    private String name;
    private int status;   //判断是有火车
    private int max_speed;
    private String avg_speed;
    private String create_time;
    private String update_time;


}
