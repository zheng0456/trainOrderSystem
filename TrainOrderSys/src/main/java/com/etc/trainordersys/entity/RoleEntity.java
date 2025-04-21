package com.etc.trainordersys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
