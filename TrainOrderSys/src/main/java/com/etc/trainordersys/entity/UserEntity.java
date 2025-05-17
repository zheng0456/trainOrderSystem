package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private int user_id;      //用户id
    private String username;  //用户网名
    private String password;  //用户密码
    private String name;      //乘车人姓名
    private int sex;          //性别
    private String birthday;  //出生日期
    private String phone;     //用户手机号
    private String email;     //邮件
    private String address;    //地址
    private String head_pic;  //头像
    private int status;       //状态
    private int role_id;      //1--管理员，2--用户
    private String card_code;  //身份证号
    private int user_type;   //用户优惠类型（0-成人，1-儿童，2-学生，3-残疾军人）
    private int discount_num; //打折数
    private String create_time;
    private String update_time;
    private int card_type;  //证件类型
//     <option value="0" selected>中国居民身份证</option>
//     <option value="1">港澳居民居住证</option>
//     <option value="2">台湾居民居住证</option>
//     <option value="3">外国人永久居留身份证</option>
//     <option value="4">外国护照</option>
//     <option value="5">中国护照</option>
//     <option value="6">港澳居民来往内地通行证</option>
//     <option value="7">台湾居民来往大陆通行证</option>
    private int del_flag; //逻辑删除（默认为1，存在状态）

    private int face_register;//是否人脸注册 1-未注册 2-已注册
}
