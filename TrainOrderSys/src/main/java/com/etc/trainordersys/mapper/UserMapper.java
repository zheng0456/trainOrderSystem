package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //登录
    @Select("select * from t_user where (username = #{account} or email = #{account} or phone = #{account}) and password = #{password}")
    UserEntity login(String account, String password);
    //注册-1 验证用户名
    @Select("select * from t_user where username = #{username}")
    UserEntity checkUserName(String username);
    //注册-2 验证证件号
    @Select("select * from t_user where card_code = #{cardCode}")
    UserEntity checkCardCode(String cardCode);
    //注册-3 验证手机号
    @Select("select * from t_user where phone = #{phone}")
    UserEntity checkPhone(String phone);
    //注册-4 验证邮箱格式
    @Select("select * from t_user where email = #{email}")
    UserEntity checkEmail(String email);
    //用户注册
    @Insert("insert into t_user values(null,#{username},#{password},#{name},null,null,#{phone},#{email},null,null,null,2,#{card_code},#{user_type},null,now(),now(),#{card_type},1)")
    int register(UserEntity user);
    //显示编辑用户页面,查询选用的用户信息
    @Select("select * from t_user where user_id = #{userId}")
    UserEntity showEditByUserId(Integer userId);
    //修改个人信息
    @Update("update t_user set username=#{username},phone=#{phone},email=#{email},birthday=#{birthday},sex=#{sex},user_type=#{user_type} where user_id = #{user_id}")
    int editInformation(UserEntity user);
}
