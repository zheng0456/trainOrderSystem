package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerEntity {
    private int id;
    private String card_type;
    private String passenger_name;
    private String card_code;
    private String phone;
    private String passenger_type;
    private int status;
    private String userId;
    private String create_time;
    private String update_time;
}
