package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity {
    private int ticket_id;
    private String order_no;      //订单号
    private String train_number;    //火车名
    private String start_station;
    private String end_station;
    private String start_time;
    private String arrive_time;
    private String departure_date;
    private String passenger_name;   //乘车人姓名
    private String card_type;
    private String card_code;
    private int ticket_type;//成人票、儿童票等
    private int seat_type_id;
    private int carriage_number;
    private String seat_number;
    private double ticket_price;
    private String QR_code;//二维码
    private String create_time;
    private String update_time;
    private int ticket_status;
    private SeatTypeEntity seatType;//一张火车票只能有一种座位类型
}
