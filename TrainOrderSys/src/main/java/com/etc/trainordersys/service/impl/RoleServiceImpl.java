package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.MenuEntity;
import com.etc.trainordersys.entity.RoleAuthorityEntity;
import com.etc.trainordersys.entity.RoleEntity;
import com.etc.trainordersys.mapper.RoleMapper;
import com.etc.trainordersys.service.IRoleService;
import com.etc.trainordersys.utils.MenuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    //搜索+分页查询所有角色列表的方法
    @Override
    public List<RoleEntity> findAllRolePageList(String name) {
        return roleMapper.findAllRolePageList(name);
    }

    @Override
    public void findAllMenuList(Model model) {
        List<MenuEntity> lists=roleMapper.findAllMenuList();
        for (int i = 0; i < lists.size(); i++) {
            Integer parent_id=lists.get(i).getParent_id();
            MenuEntity menu=roleMapper.findMenuByParentId(parent_id);
            if (menu!=null && menu.getParent_id()!=0){
                menu.setParent(roleMapper.findMenuByParentId(menu.getParent_id()));
            }
            lists.get(i).setParent(menu);
        }
        List<MenuEntity> topMenus =  MenuUtils.getTopMenus(lists);
        model.addAttribute("topMenus",topMenus);
        List<MenuEntity> secondMenus =  MenuUtils.getSecondMenus(lists);
        model.addAttribute("secondMenus",secondMenus);
        List<MenuEntity> thirdMenus =  MenuUtils.getThirdMenus(lists);
        model.addAttribute("thirdMenus",thirdMenus);
    }

    //保存添加菜单信息
    @Override
    public String addMenu(MenuEntity menu) {
        return roleMapper.addMenu(menu)>0?"success":"fail";
    }

    //根据菜单id查询菜单详情
    @Override
    public MenuEntity findMenuById(int menuId) {
        return roleMapper.findMenuById(menuId);
    }

    @Override
    public boolean editMenu(MenuEntity menu) {
        return roleMapper.editMenu(menu);
    }
    //保存的添加角色信息
    @Transactional

    @Override
    public boolean addRole(RoleEntity role) {
        int res=roleMapper.addRole(role);
        if (res>0){
            roleMapper.addRoleAuthority(role.getRole_id(),role.getAuthorities());
            return true;
        }
        return false;
    }

    @Override
    public RoleEntity findRoleById(int roleId) {
        return roleMapper.findRoleById(roleId);
    }

    @Transactional
    @Override
    public boolean editRole(RoleEntity role) {
        int res=roleMapper.updateRole(role);
        if (res>0){
            roleMapper.deleteRoleAuthorities(role.getRole_id(),role.getAuthorities());
            roleMapper.addRoleAuthority(role.getRole_id(),role.getAuthorities());
            return true;
        }
        return false;
    }
    //关闭角色（停用后可以通过编辑角色重新启动，逻辑删除）
    @Override
    public String deleteRole(int roleId) {
        Integer userId=roleMapper.findUserByRole(roleId);
        if (userId!=null){
            return "存在用户使用此角色，请先处理再删除";
        }else {
            int res=roleMapper.deleteRole(roleId);
            return res>0?"success":"fail";
        }
    }
    //删除菜单
    @Override
    public String deleteMenu(int menuId) {
        Integer roleId=roleMapper.findRoleByMenuId(menuId);
        if (roleId!=null){
            return "有角色拥有操作此菜单的权限，请处理完角色权限，再删除菜单";
        }else {
            Integer mid=roleMapper.findMenuChild(menuId);
            if (mid!=null){
                return "请先删除该菜单下的子菜单，否则无法删除";
            }else {
                int res=roleMapper.deleteMenu(menuId);
                return res>0?"success":"fail";
            }
        }
    }
}
