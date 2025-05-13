package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.PassengerEntity;
import com.etc.trainordersys.entity.UserEntity;
import com.etc.trainordersys.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping("/view/user/checkUserName")
    public @ResponseBody boolean checkUserName(String username){
        UserEntity user = userService.checkUserName(username);
        if (user==null){
            return true;
        }
        return false;
    }
    //注册-2 验证证件号
    @RequestMapping("/view/user/checkCardCode")
    public @ResponseBody boolean checkCardCode(String card_code){
        UserEntity user = userService.checkCardCode(card_code);
        if (user==null){
            return true;
        }
        return false;
    }
    //注册-3 验证手机号
    @RequestMapping("/view/user/checkPhone")
    public @ResponseBody boolean checkPhone(String phone){
        UserEntity user = userService.checkPhone(phone);
        if (user==null){
            return true;
        }
        return false;
    }
    //注册-4 验证邮箱格式
    @RequestMapping("/view/user/checkEmail")
    public @ResponseBody boolean checkEmail(String email){
        UserEntity user = userService.checkEmail(email);
        if (user==null){
            return true;
        }
        return false;
    }

    //用户注册
    @PostMapping( "/view/user/register")
    public String register(@ModelAttribute UserEntity user){
        int res = userService.register(user);
        if (res>0){
            return "/home/login";
        }
        return "redirect:/error-page";
    }

    //退出系统
    @PostMapping("/view/user/logout")
    public @ResponseBody boolean logout(HttpSession session){
        //销毁会话
        session.invalidate();
        return true;
    }

    //显示编辑用户页面,查询选用的用户信息
    @RequestMapping("/view/personal/info")
    public String showEditByUserId(HttpSession session,Model model){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        UserEntity user = userService.showEditByUserId(user_id);
        model.addAttribute("user",user);
        return "/home/center/personal/info";
    }
    //修改个人信息
    @RequestMapping("/view/personal/editInformation")
    public String editInformation(@ModelAttribute UserEntity user,HttpSession session){
        int res = userService.editInformation(user);
        if (res>0){
            session.setAttribute("user",user);
            return "forward:/view/personal/info";
        }
        return "redirect:/error-page";
    }
    //修改头像
    @RequestMapping("/view/personal/headPic")
    public String editHeadPic(@RequestParam String head_pic,HttpSession session){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        int res = userService.editHeadPic(head_pic,user_id);
        if (res>0){
            return "forward:/view/personal/info";
        }
        return "redirect:/error-page";
    }
    //显示乘客信息页面
    @RequestMapping("/view/passenger/list")
    public String showPassenger(HttpSession session,Model model){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        //根据user_id查询本账号下所添加的所有乘车人信息
        List<PassengerEntity> passengers = userService.showPassenger(user_id);
        model.addAttribute("passengers",passengers);
        return "/home/center/passenger/passengers";
    }
    //验证乘车人证件号
    @RequestMapping("/view/user/checkPassengerCardCode")
    public @ResponseBody boolean checkPassengerCardCode(String card_code){
        UserEntity user = userService.checkPassengerCardCode(card_code);
        if (user==null){
            return true;
        }
        return false;
    }
    //验证乘车人手机号
    @RequestMapping("/view/user/checkPassengerPhone")
    public @ResponseBody boolean checkPassengerPhone(String phone){
        UserEntity user = userService.checkPassengerPhone(phone);
        if (user==null){
            return true;
        }
        return false;
    }
    //编辑乘客信息--1.显示乘客信息
    @GetMapping("/view/passenger/edit/{id}")
    public String showEdit(@PathVariable("id") Integer id,Model model){
        PassengerEntity passenger = userService.showEdit(id);
        model.addAttribute("passenger",passenger);
        return "/home/center/passenger/passenger_edit";
    }
    //2.保存编辑信息
    @PutMapping("/view/passenger/edit")
    public @ResponseBody boolean editPassenger(@ModelAttribute PassengerEntity passenger){
        //保存修改信息
        boolean res = userService.editPassenger(passenger);
        if (res){
            return true;
        }
        return false;
    }
    //删除乘客信息
    @DeleteMapping("/view/passenger/delete/{id}")
    public @ResponseBody boolean deletePassenger(@PathVariable("id") Integer id){
        boolean res = userService.deletePassenger(id);
        if (res){
            return true;
        }
        return false;
    }
    //批量删除乘客信息
    @DeleteMapping("/view/passenger/deleteAny/{ids}")
    public @ResponseBody boolean deleteSelectPassenger(@PathVariable("ids") List<Integer> ids){
        boolean res = userService.deleteSelectPassenger(ids);
        if (res){
            return true;
        }
        return false;
    }
    //跳转添加乘车人信息页面
    @RequestMapping("/view/passenger/showAdd")
    public String showAddPassenger(){
        return "/home/center/passenger/passenger_add";
    }
    //添加乘车人信息
    @PostMapping("/view/passenger/add")
    public @ResponseBody boolean addPassenger(@ModelAttribute PassengerEntity passenger,HttpSession session){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        //插入乘车人信息
        boolean res = userService.addPassenger(passenger,user_id);
        if (res){
            return true;
        }
        return false;
    }

}
