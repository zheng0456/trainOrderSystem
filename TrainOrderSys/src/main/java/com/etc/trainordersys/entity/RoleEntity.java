package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    private int role_id;
    private String name;
    private String remark;
    private String status;
    private String create_time;
    private String update_time;
    private String del_flag;
    private List<Integer> authority;//一个角色对应可操作的菜单id列表
    private List<MenuEntity> menus;//多对多的关系，一个角色拥有多个权限
}
