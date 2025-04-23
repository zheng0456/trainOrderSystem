package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.UserEntity;
import com.etc.trainordersys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    @Qualifier("userService")
    IUserService userService;

    //登录
    @RequestMapping("/home/login")
    public @ResponseBody String login(String account,String password){
        UserEntity user = userService.login(account,password);
        if (user!=null){
            if (user.getRole_id()==2){
                return "user_page";
            }else {
                return "admin_page";
            }
        }
        return "用户名或者密码错误";
    }

    //注册-1 验证用户名
    @RequestMapping("/system/user/checkUserName")
    public @ResponseBody boolean checkUserName(String username){
        UserEntity user = userService.checkUserName(username);
        if (user==null){
            return true;
        }
        return false;
    }
    //注册-2 验证证件号
    @RequestMapping("/system/user/checkCardCode")
    public @ResponseBody boolean checkCardCode(String card_code){
        UserEntity user = userService.checkCardCode(card_code);
        if (user==null){
            return true;
        }
        return false;
    }
    //注册-3 验证手机号
    @RequestMapping("/system/user/checkPhone")
    public @ResponseBody boolean checkPhone(String phone){
        UserEntity user = userService.checkPhone(phone);
        if (user==null){
            return true;
        }
        return false;
    }
    //注册-4 验证邮箱格式
    @RequestMapping("/system/user/checkEmail")
    public @ResponseBody boolean checkEmail(String email){
        UserEntity user = userService.checkEmail(email);
        if (user==null){
            return true;
        }
        return false;
    }

    //用户注册
    @PostMapping( "/system/user/register")
    public String register(@ModelAttribute UserEntity user){
        System.out.println(user);
        int res = userService.register(user);
        if (res>0){
            return "/home/login";
        }
        return "注册失败";
    }
}
