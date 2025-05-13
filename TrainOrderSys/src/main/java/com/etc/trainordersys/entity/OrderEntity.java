package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    private String order_no;
    private String train_number;
    private String departure_date;
    private int ticket_quantity;//购买票数
    private double total_price;
    private int payment;//0-未支付 1-已支付
    private int user_id;
    private String order_remark;
    private String create_time;
    private int order_status;//0-取消订单 1-默认为1
    private List<TicketEntity> tickets;

    public OrderEntity(String order_no,String train_number,String departure_date,int user_id){
        this.order_no=order_no;
        this.train_number=train_number;
        this.departure_date=departure_date;
        this.user_id=user_id;
    }
}
