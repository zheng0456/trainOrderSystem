package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.UserEntity;
import com.etc.trainordersys.mapper.UserMapper;
import com.etc.trainordersys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    //登录
    @Override
    public UserEntity login(String account, String password) {
        return userMapper.login(account,password);
    }
    //注册-1 验证用户名
    @Override
    public UserEntity checkUserName(String username) {
        return userMapper.checkUserName(username);
    }
    //注册-2 验证证件号
    @Override
    public UserEntity checkCardCode(String cardCode) {
        return userMapper.checkCardCode(cardCode);
    }
    //注册-3 验证手机号
    @Override
    public UserEntity checkPhone(String phone) {
        return userMapper.checkPhone(phone);
    }
    //注册-4 验证邮箱格式
    @Override
    public UserEntity checkEmail(String email) {
        return userMapper.checkEmail(email);
    }
}
