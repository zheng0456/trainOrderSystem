package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.UserEntity;
import com.etc.trainordersys.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    @Qualifier("userService")
    IUserService userService;

    //登录
    @RequestMapping("/home/login")
    public @ResponseBody String login(String account, String password, HttpSession session){
        UserEntity user = userService.login(account,password);
        if (user!=null){
            session.setAttribute("user",user);
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
        int res = userService.register(user);
        if (res>0){
            return "/home/login";
        }
        return "redirect:/error-page";
    }

    //退出系统
    @PostMapping("/system/user/logout")
    public @ResponseBody boolean logout(HttpSession session){
        //销毁会话
        session.invalidate();
        return true;
    }

    //显示编辑用户页面,查询选用的用户信息
    @RequestMapping("/system/personal/info")
    public String showEditByUserId(HttpSession session,Model model){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        UserEntity user = userService.showEditByUserId(user_id);
        model.addAttribute("user",user);
        return "/home/center/personal/info";
    }
    //修改个人信息
    @RequestMapping("/system/personal/editInformation")
    public String editInformation(@ModelAttribute UserEntity user,HttpSession session){
        int res = userService.editInformation(user);
        if (res>0){
            session.setAttribute("user",user);
            return "forward:/system/personal/info";
        }
        return "redirect:/error-page";
    }
    //修改头像
    @RequestMapping("/system/personal/headPic")
    public String editHeadPic(@RequestParam String head_pic,HttpSession session){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        int res = userService.editHeadPic(head_pic,user_id);
        if (res>0){
            return "forward:/system/personal/info";
        }
        return "redirect:/error-page";
    }
}
