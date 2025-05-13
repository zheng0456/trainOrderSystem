package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 重复购票
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepeatedTicketsEntity {
    private String train_number;   //火车名
    private String order_no;  //订单号
    private String departure_date;  //火车开始日期
    private int user_id;     //用户id

    public RepeatedTicketsEntity(int user_id,String train_number,String order_no,String departure_date){
        this.departure_date=departure_date;
        this.user_id=user_id;
        this.order_no=order_no;
        this.train_number=train_number;
    }
}
