package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.PassengerEntity;
import com.etc.trainordersys.entity.UserEntity;
import com.etc.trainordersys.mapper.UserMapper;
import com.etc.trainordersys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    //用户注册
    @Override
    public int register(UserEntity user) {
        return userMapper.register(user);
    }
    //显示编辑用户页面,查询选用的用户信息
    @Override
    public UserEntity showEditByUserId(Integer userId) {
        return userMapper.showEditByUserId(userId);
    }
    //修改个人信息
    @Override
    public int editInformation(UserEntity user) {
        return userMapper.editInformation(user);
    }
    //修改头像
    @Override
    public int editHeadPic(String headPic, Integer userId) {
        return userMapper.editHeadPic(headPic,userId);
    }
    //根据user_id查询本账号下所添加的所有乘车人信息
    @Override
    public List<PassengerEntity> showPassenger(Integer userId) {
        return userMapper.showPassenger(userId);
    }
    //编辑乘客信息--1.显示乘客信息
    @Override
    public PassengerEntity showEdit(Integer id) {
        return userMapper.showEdit(id);
    }
    //保存修改信息
    @Override
    public boolean editPassenger(PassengerEntity passenger) {
        return userMapper.editPassenger(passenger);
    }
    //验证乘车人证件号
    @Override
    public UserEntity checkPassengerCardCode(String cardCode) {
        return userMapper.checkPassengerCardCode(cardCode);
    }
    //验证乘车人手机号
    @Override
    public UserEntity checkPassengerPhone(String phone) {
        return userMapper.checkPassengerPhone(phone);
    }
    //删除乘客信息
    @Override
    public boolean deletePassenger(Integer id) {
        return userMapper.deletePassenger(id);
    }
    //批量删除乘客信息
    @Override
    public boolean deleteSelectPassenger(List<Integer> ids) {
        for (Integer id:ids){
            userMapper.deletePassenger(id);
        }
        return true;
    }
    //新增乘车人信息
    @Override
    public boolean addPassenger(PassengerEntity passenger, Integer userId) {
        return userMapper.addPassenger(passenger,userId);
    }
    //旧密码校验
    @Override
    public UserEntity checkOldPassword(Integer userId, String password) {
        return userMapper.checkOldPassword(userId,password);
    }
    //修改密码
    @Override
    public boolean editPassword(String password, Integer userId) {
        return userMapper.editPassword(password,userId);
    }
    //修改手机号
    @Override
    public boolean editPhone(String phone, Integer userId) {
        return userMapper.editPhone(phone,userId);
    }
    //修改邮箱
    @Override
    public boolean editEmail(String email, Integer userId) {
        return userMapper.editEmail(email,userId);
    }
    //查询用户列表
    @Override
    public List<UserEntity> findUserList(String userName) {
        return userMapper.findUserList(userName);
    }
    //添加用户-2.保存用户信息
    @Override
    public boolean addUser(UserEntity user) {
        return userMapper.addUser(user);
    }
    //显示编辑用户页面,查询选用的用户信息
    @Override
    public UserEntity findUserById(int userId) {
        return userMapper.findUserById(userId);
    }
    //保存修改的用户信息
    @Override
    public boolean editUser(UserEntity user) {
        return userMapper.editUser(user);
    }
    //删除用户，物理删除，实际应该是逻辑删除
    @Override
    public boolean deleteUser(int userId) {
        return userMapper.deleteUser(userId);
    }
}
