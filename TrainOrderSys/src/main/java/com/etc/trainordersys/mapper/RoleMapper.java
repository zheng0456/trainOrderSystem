package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.MenuEntity;
import com.etc.trainordersys.entity.RoleEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper
{
    @Select(" <script> " +
            " select * from t_role "+
            " <where> " +
            " <if test='name!=null'> name like '%' #{name} '%' </if> "+
            " </where>" +
            " </script> ")
    List<RoleEntity> findAllRolePageList(String name);

    //查询所有菜单列表
    @Select("select * from t_menu")
    List<MenuEntity> findAllMenuList();

    //根据父菜单id查询菜单详情
    @Select("select * from t_menu where menu_id=#{parentId}")
    MenuEntity findMenuByParentId(Integer parentId);

    //保存添加菜单信息
    @Insert("insert into t_menu values(null,now(),now(),#{menu_name},#{url},#{icon},#{sort},#{parent_id},#{is_button},#{is_show},#{remark})")
    int addMenu(MenuEntity menu);
}
