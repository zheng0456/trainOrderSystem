package com.etc.trainordersys.entity;

import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//火车车厢实体类
public class TrainCarriageEntity {
    private int id;
    private int carriage_number;    //车厢号
    private int train_code;      //
    private int carriage_type;   //车箱类型   与座位类型相同
    private int seat_nums;     //座位号
    private int emp_seats;   //无座
    private String create_time;
    private String update_time;

}
