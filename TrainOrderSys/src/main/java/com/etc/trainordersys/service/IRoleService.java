package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.MenuEntity;
import com.etc.trainordersys.entity.RoleEntity;
import org.springframework.ui.Model;

import java.util.List;

public interface IRoleService {
    //搜索+分页查询所以角色列表方法
    List<RoleEntity> findAllRolePageList(String name);
    //显示添加角色界面，查询所有菜单列表
    void findAllMenuList(Model model);
    //保存添加菜单的信息
    String addMenu(MenuEntity menu);

    //根据菜单id查询菜单详情
    MenuEntity findMenuById(int menuId);
    //保存编辑的菜单信息
    boolean editMenu(MenuEntity menu);
}
