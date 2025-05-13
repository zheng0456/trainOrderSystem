package com.etc.trainordersys.entity;

import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存放座位信息实体类
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeatInfoDTO {
    private String [] passengerName;   //乘车人名字，用户选择的乘车人
    private String [] cardType;     //乘车人证件类型
    private String [] cardCode;     //乘车人证件号
    private String [] ticketType;   //车票类型
    private int [] seatTypeId;    //座位类型id
    private double [] ticketPrice;  //票价格

    private String [] seatNum;    //座位编号
}
