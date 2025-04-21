package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuEntity {
    private int menu_id;
    private String create_time;
    private String update_time;
    private String menu_name;
    private String url;
    private String icon;
    private int sort;//菜单排序
    private int parent_id;
    private int is_button;
    private int is_show;
    private String remark;
}
