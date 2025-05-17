package com.etc.trainordersys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etc.trainordersys.entity.MenuEntity;
import com.etc.trainordersys.entity.PassengerEntity;
import com.etc.trainordersys.entity.RoleEntity;
import com.etc.trainordersys.entity.UserEntity;
import com.etc.trainordersys.service.IRoleService;
import com.etc.trainordersys.service.IUserService;
import com.etc.trainordersys.utils.FaceUtils;
import com.etc.trainordersys.utils.MenuUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class UserController {
    @Autowired
    @Qualifier("userService")
    IUserService userService;
    //人脸注册
    @PostMapping("/home/faceRegist")
    public @ResponseBody String faceRegister(String img,HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");
        //调用工具类封装的方法进行注册
        String resp = FaceUtils.faceRegister(img,user.getUsername(),String.valueOf(user.getUser_id()),"login_face_ticket_1");
        //解析结果
        JSONObject jsonObject = JSONObject.parseObject(resp);
        //判断是否成功
        String msg = jsonObject.get("error_msg").toString();
        if ("SUCCESS".equals(msg)){
            //修改用户已经注册的标识
            userService.updateIsFace(user.getUser_id());
            return "SUCCESS";
        }
        return "FAIL";
    }
    //人脸登录
    //人脸注册
    @PostMapping("/home/faceLogin")
    public @ResponseBody String faceLogin(String img,HttpSession session){
        //调用工具类封装的方法进行登录
        String resp = FaceUtils.faceLogin(img,"login_face_ticket_1");
        //解析结果
        JSONObject jsonObject = JSONObject.parseObject(resp);
        //判断是否成功
        String msg = jsonObject.get("error_msg").toString();
        if ("SUCCESS".equals(msg)){
            //登录成功，解析json数据中的userid
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray userList = result.getJSONArray("user_list");
            JSONObject info = userList.getJSONObject(0);
            String userId = info.getString("user_id");
            //根据userid查询用户详细
            UserEntity user = userService.findUserById(Integer.parseInt(userId));
            //修改用户已经注册的标识
            userService.updateIsFace(user.getUser_id());
            session.setAttribute("user",user);
            return "SUCCESS";
        }
        return "FAIL";
    }

    //登录
    @RequestMapping("/home/login")
    public @ResponseBody String login(String account, String password, HttpSession session){
        UserEntity user = userService.login(account,password);
        if (user!=null){
            session.setAttribute("user",user);
            if (user.getRole_id()==2){
                return "user_page";
            }else {
                //(1)根据用户信息，获取角色id，根据角色id查询该用户下所有的菜单列表
                List<MenuEntity> menuList = roleService.findUserRoleMenuList(user.getRole_id());
                // (2)获取一级菜单
                List<MenuEntity> topMenus = MenuUtils.getTopMenus(menuList);
                //(3)获取二级菜单
                List<MenuEntity> secondMenus = MenuUtils.getSecondMenus(menuList);
                //(4)把获取的数据保存到session
                session.setAttribute("userTopMenus", topMenus);
                session.setAttribute("userSecondMenus", secondMenus);
                session.setAttribute("menuList", menuList);
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
    public String showPassenger(HttpSession session,Model model,
                                @RequestParam(value = "passenger_name",required = false)String passenger_name){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        //根据user_id查询本账号下所添加的所有乘车人信息
        List<PassengerEntity> passengers = userService.showPassenger(user_id,passenger_name);
        model.addAttribute("passengers",passengers);
        if (passenger_name!=null){
            model.addAttribute("passenger_name",passenger_name);
        }
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
    //跳转账号安全页面
    @RequestMapping("/view/personal/security")
    public String showSecurity(){
        return "/home/center/personal/security";
    }
    //跳转修改密码页面
    @RequestMapping("/view/personal/showEditPassword")
    public String showEditPassword(){
        return "/home/center/personal/updatePwd";
    }
    //旧密码校验
    @RequestMapping("/view/personal/checkOldPassword")
    public @ResponseBody boolean checkOldPassword(@RequestParam(value = "password") String password,
                                                  @RequestParam(value = "user_id") Integer user_id){
        UserEntity user = userService.checkOldPassword(user_id,password);
        if (user!=null){
            return true;
        }else {
            return false;
        }
    }
    //修改密码
    @RequestMapping("/view/personal/editPassword")
    public @ResponseBody boolean editPassword(@RequestParam(value = "password") String password,
                                              @RequestParam(value = "user_id") Integer user_id){
        boolean res = userService.editPassword(password,user_id);
        if (res){
            return true;
        }else {
            return false;
        }
    }
    //跳转修改手机号页面
    @RequestMapping("/view/personal/showEditPhone")
    public String showEditPhone(HttpSession session,Model model){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        UserEntity user = userService.showEditByUserId(user_id);
        model.addAttribute("phone",user.getPhone());
        return "/home/center/personal/updatePhone";
    }
    //修改手机号
    @RequestMapping("/view/personal/editPhone")
    public @ResponseBody boolean editPhone(@RequestParam(value = "phone") String phone,
                                              @RequestParam(value = "user_id") Integer user_id){
        boolean res = userService.editPhone(phone,user_id);
        if (res){
            return true;
        }else {
            return false;
        }
    }
    //跳转修改邮箱页面
    @RequestMapping("/view/personal/showEditEmail")
    public String showEditEmail(HttpSession session,Model model){
        //获取session中的用户信息
        Integer user_id = ((UserEntity)session.getAttribute("user")).getUser_id();
        UserEntity user = userService.showEditByUserId(user_id);
        model.addAttribute("email",user.getEmail());
        return "/home/center/personal/updateEmail";
    }
    //修改邮箱
    @RequestMapping("/view/personal/editEmail")
    public @ResponseBody boolean editEmail(@RequestParam(value = "email") String email,
                                           @RequestParam(value = "user_id") Integer user_id){
        boolean res = userService.editEmail(email,user_id);
        if (res){
            return true;
        }else {
            return false;
        }
    }
    //跳转修改通知的页面
    @RequestMapping("/view/personal/showNotice")
    public String showNotice(){
        return "/home/center/personal/updateNotice";
    }
    //查询用户列表
    @RequestMapping("/system/user/list")
    public String findUserList(Model model, @RequestParam(value="username",required=false)String userName,
                               @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
        //1.方法入参
        //2.每页展示的条数
        int pageSize=2;
        //3.设置起始页和每页展示的条数
        PageHelper.startPage(currentPage,pageSize);
        //4.分页查询用户列表，如果有搜索条件就模糊查询，没有就分页查询所有用户列表
        List<UserEntity> users=userService.findUserList(userName);
        //5.封装结果集
        PageInfo<UserEntity> pageInfo =new PageInfo<>(users);
        //6.把数据保存到model中
        model.addAttribute("page",pageInfo);
        //7.如果有搜索条件，把username也保存到model中
        if (userName!=null){
            model.addAttribute("username",userName);
        }
        //8.请求转发
        return "/admin/user/list";
    }
    @Autowired
    @Qualifier("roleService")
    private IRoleService roleService;
    //添加用户-1.显示用户添加页面，查询所有的角色列表
    @RequestMapping("/system/user/add")
    public String showAdd(Model model){
        //调用查询所有角色列表的方法
        List<RoleEntity> roles=roleService.findAllRoleList();
        model.addAttribute("roles",roles);
        return "/admin/user/add";
    }
    //添加用户-2.保存用户信息
    @PostMapping("/system/user/add")
    public @ResponseBody String addUser(@Validated UserEntity user){
        return userService.addUser(user)==true?"true":"fail";
    }
    //显示编辑用户页面,查询选用的用户信息
    @GetMapping("/system/user/edit/{id}")
    public String showEdit(@PathVariable("id")int user_id,Model model){
        UserEntity user=userService.findUserById(user_id);//根据用户id查询用户详情
        List<RoleEntity> roles=roleService.findAllRoleList();//查询所有角色列表
        model.addAttribute("user",user);
        model.addAttribute("roles",roles);
        return "/admin/user/edit";
    }
    //保存修改的用户信息
    @PutMapping("/system/user/edit")
    public @ResponseBody String editUser(@Validated UserEntity user){
        return userService.editUser(user)==true?"true":"fail";
    }
    //删除用户，物理删除，实际应该是逻辑删除
    @DeleteMapping("/system/user/delete")
    public @ResponseBody boolean deleteUser(@RequestParam("id")int user_id){
        return userService.deleteUser(user_id);
    }
}
