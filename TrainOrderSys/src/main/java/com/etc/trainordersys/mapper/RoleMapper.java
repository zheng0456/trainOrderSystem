package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.MenuEntity;
import com.etc.trainordersys.entity.RoleEntity;
import org.apache.ibatis.annotations.*;

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

    //根据菜单id查询菜单详情
    @Select("select * from t_menu where menu_id=#{menuId}")
    MenuEntity findMenuById(int menuId);

    @Update("update t_menu set update_time=now(),menu_name=#{menu_name},url=#{url},icon=#{icon},sort=#{sort},parent_id=#{parent_id},is_button=#{is_button},is_show=#{is_show},remark=#{remark}" +
            " where menu_id=#{menu_id}")
    boolean editMenu(MenuEntity menu);

    @Insert("insert into t_role values(null,#{name},#{remark},#{status},now(),now(),#{del_flag})")
    @Options(useGeneratedKeys = true,keyProperty = "role_id",keyColumn = "role_id")
    int addRole(RoleEntity role);

    @Insert(" <script> " +
            " insert into t_role_authority values "+
            " <foreach collection='authorities' item='mid' index='index' separator=','> "+
            " (#{roleId},#{mid}) " +
            " </foreach>" +
            " </script> ")
    void addRoleAuthority(int roleId, List<Integer> authorities);

    @Select("select * from t_role where role_id = #{roleId}")
    @Results({
            @Result(column = "role_id",property = "role_id"),
            @Result(column = "name",property = "name"),
            @Result(column = "remark",property = "remark"),
            @Result(column = "role_id",
                    property = "authorities",
                    javaType = List.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.RoleMapper.findAuthorities"))
    })
    RoleEntity findRoleById(int roleId);
    @Select("select authority_id from t_role_authority where role_id = #{roleId}")
    List<Integer> findAuthorities(int roleId);

    @Update("update t_role set name=#{name},remark=#{remark},status=#{status},update_time=now() where role_id=#{role_id}")
    int updateRole(RoleEntity role);

    @Delete("delete from t_role_authority where role_id=#{roleId}")
    void deleteRoleAuthorities(int roleId, List<Integer> authorities);

    @Select("select user_id from t_user where role_id=#{roleId} limit 1")
    Integer findUserByRole(int roleId);

    @Update(("update t_role set status=0 where role_id=#{roleId}"))
    int deleteRole(int roleId);

    @Select("select role_id from t_role_authority where authority_id=#{menuId} limit 1")
    Integer findRoleByMenuId(int menuId);
    @Select("select * from t_menu where parent_id=#{menuId} limit 1")
    Integer findMenuChild(int menuId);
    @Delete("delete from t_menu where menu_id=#{menuId}")
    int deleteMenu(int menuId);
}
