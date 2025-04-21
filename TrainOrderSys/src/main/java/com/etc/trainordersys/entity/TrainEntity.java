package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainEntity {
    private int id;
    private int train_code;
    private int train_type;
    private int status;
    private String create_time;
    private String update_time;
    private int del_flag;

}
