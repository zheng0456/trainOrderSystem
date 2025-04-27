package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.MenuEntity;
import com.etc.trainordersys.entity.RoleAuthorityEntity;
import com.etc.trainordersys.entity.RoleEntity;
import com.etc.trainordersys.service.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@Scope
public class RoleController {
    @Autowired
    @Qualifier("roleService")
    private IRoleService roleService;
    @GetMapping("/system/role/list")
    public String findAllRoleList(Model model, @RequestParam(value = "name",required = false)String name,
                                  @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
        int pageSize=2;
        PageHelper.startPage(currentPage,pageSize);
        List<RoleEntity> roleList=roleService.findAllRolePageList(name);
        PageInfo<RoleEntity> pageInfo=new PageInfo<>(roleList);
        model.addAttribute("page",pageInfo);
        if (name!=null){
            model.addAttribute("roleName",name);
        }
        log.info("角色列表，roleList={}",roleList);
        return "/admin/role/list";
    }
    //显示添加角色页面，查询所有菜单列表
    @GetMapping("/system/role/add")
    public String showRoleAdd(Model model){
        roleService.findAllMenuList(model);
        return "/admin/role/add";
    }
    //保存添加的角色信息
    @PostMapping("/system/role/add")
    public @ResponseBody String addRole(RoleEntity role){
        boolean res=roleService.addRole(role);
        return res==true?"success":"fail";
    }
    //查询所有菜单列表
    @GetMapping("/system/menu/list")
    public String findAllMenuList(Model model){
        roleService.findAllMenuList(model);
        return "/admin/menu/list";
    }
    //显示添加菜单界面
    @GetMapping("/system/menu/add")
    public String showAddMenu(Model model){
        roleService.findAllMenuList(model);
        return "/admin/menu/add";
    }
    //保存添加菜单的信息
    @PostMapping("/system/menu/add")
    public @ResponseBody String addMenu(MenuEntity menu){
        return roleService.addMenu(menu);
    }
    //显示编辑菜单的页面
    @GetMapping("/system/menu/edit/{menu_id}")
    public String showEditMenu(@PathVariable("menu_id") int menuId,Model model){
        roleService.findAllMenuList(model);
        MenuEntity menu=roleService.findMenuById(menuId);
        model.addAttribute("menu",menu);
        return "/admin/menu/edit";
    }
    //保存编辑的菜单信息
    @PutMapping("/system/menu/edit")
    public @ResponseBody String editMenu(MenuEntity menu){
        log.info("编辑菜单信息的方法入参：menu={}",menu);
        return roleService.editMenu(menu)==true?"success":"fail";
    }
}
