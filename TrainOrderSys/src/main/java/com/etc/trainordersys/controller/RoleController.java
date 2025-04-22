package com.etc.trainordersys.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/system/role/add")
    public String showRoleAdd(Model model){
        roleService.findAllMenuList(model);
        return "/admin/role/add";
    }
}
