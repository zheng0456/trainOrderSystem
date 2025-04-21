package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainTypeEntity {
    private int type_id;
    private String name;
    private int status;
    private int max_speed;
    private String avg_speed;
    private String create_time;
    private String update_time;
}
