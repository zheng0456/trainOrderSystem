package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private int user_id;      //用户账号
    private String username;  //用户网名
    private String password;  //用户密码
    private String name;      //乘车人姓名
    private int sex;          //性别
    private String birthday;  //出生日期
    private String phone;     //用户手机号
    private String email;     //邮件
    private String adress;    //地址
    private String head_pic;  //头像
    private int status;       //状态
    private int role_id;      //角色id
    private String card_code;  //
    private int user_type;
    private int discount_num;
    private String create_time;
    private String update_time;
    private int card_type;
    private int del_flag;
}
