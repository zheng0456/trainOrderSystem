package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//火车实体类
public class TrainEntity {
    private int id;
    private int train_code;
    private int train_type;
    private int status;  //判断是否有票
    private String create_time;
    private String update_time;
    private int carriage_num;
    private int del_flag;

    private TrainTypeEntity trainType;  //火车类型

}
