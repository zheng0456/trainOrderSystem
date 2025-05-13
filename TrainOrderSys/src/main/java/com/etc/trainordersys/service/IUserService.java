package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.PassengerEntity;
import com.etc.trainordersys.entity.UserEntity;

import java.util.List;

public interface IUserService {
    //登录
    UserEntity login(String account, String password);
    //注册-1 验证用户名
    UserEntity checkUserName(String username);
    //注册-2 验证证件号
    UserEntity checkCardCode(String cardCode);
    //注册-3 验证手机号
    UserEntity checkPhone(String phone);
    //注册-4 验证邮箱格式
    UserEntity checkEmail(String email);
    //用户注册
     int register(UserEntity user);
    //显示编辑用户页面,查询选用的用户信息
    UserEntity showEditByUserId(Integer userId);
    //修改个人信息
    int editInformation(UserEntity user);
    //修改头像
    int editHeadPic(String headPic, Integer userId);
    //根据user_id查询本账号下所添加的所有乘车人信息
    List<PassengerEntity> showPassenger(Integer userId);
    //编辑乘客信息--1.显示乘客信息
    PassengerEntity showEdit(Integer id);
    //保存修改信息
    boolean editPassenger(PassengerEntity passenger);
    //验证乘车人证件号
    UserEntity checkPassengerCardCode(String cardCode);
    //验证乘车人手机号
    UserEntity checkPassengerPhone(String phone);
    //删除乘客信息
    boolean deletePassenger(Integer id);
    //批量删除乘客信息
    boolean deleteSelectPassenger(List<Integer> ids);
    //新增乘车人信息
    boolean addPassenger(PassengerEntity passenger, Integer userId);
}
