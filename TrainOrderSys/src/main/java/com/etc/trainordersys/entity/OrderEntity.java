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
    private int payment;//支付
    private int user_id;
    private String order_remark;
    private String create_time;
    private int order_status;
    private List<TicketEntity> tickets;
}
