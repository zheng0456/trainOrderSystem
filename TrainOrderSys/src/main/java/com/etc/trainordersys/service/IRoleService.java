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

    String addMenu(MenuEntity menu);
}
