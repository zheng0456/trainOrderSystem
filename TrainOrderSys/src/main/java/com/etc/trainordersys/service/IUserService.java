package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.UserEntity;

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
}
