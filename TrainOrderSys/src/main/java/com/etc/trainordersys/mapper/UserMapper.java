package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
