package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.PassengerEntity;
import com.etc.trainordersys.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
    @Update("update t_user set username=#{username},phone=#{phone},email=#{email},birthday=#{birthday},sex=#{sex},user_type=#{user_type},head_pic=#{headPic},update_time=now() where user_id = #{user_id}")
    int editInformation(UserEntity user);
    //修改头像
    @Update("update t_user set head_pic=#{headPic} where user_Id=#{userId}")
    int editHeadPic(String headPic, Integer userId);

    //根据user_id查询本账号下所添加的所有乘车人信息
    @Select("select * from t_passenger where userId = #{userId}")
    List<PassengerEntity> showPassenger(Integer userId);
    //编辑乘客信息--1.显示乘客信息
    @Select("select * from t_passenger where id=#{id}")
    PassengerEntity showEdit(Integer id);
    //保存修改信息
    @Update("update t_passenger set card_type=#{card_type},passenger_name=#{passenger_name},card_code=#{card_code},phone=#{phone},passenger_type=#{passenger_type},update_time=now() where id=#{id}")
    boolean editPassenger(PassengerEntity passenger);
    //验证乘车人证件号
    @Select("select card_code from t_passenger where card_code = #{cardCode}")
    UserEntity checkPassengerCardCode(String cardCode);
    //验证乘车人手机号
    @Select("select phone from t_passenger where phone = #{phone}")
    UserEntity checkPassengerPhone(String phone);
    //删除乘客信息
    @Delete("delete from t_passenger where id=#{id}")
    boolean deletePassenger(Integer id);
    //新增乘车人信息
    @Insert("insert into t_passenger values(null,#{passenger.card_type},#{passenger.passenger_name},#{passenger.card_code},#{passenger.phone},#{passenger.passenger_type},1,#{userId},now(),now())")
    boolean addPassenger(PassengerEntity passenger, Integer userId);
    //旧密码校验
    @Select("select * from t_user where user_id=#{userId} and password=#{password}")
    UserEntity checkOldPassword(Integer userId, String password);
    //修改密码
    @Update("update t_user set password=#{password} where user_id=#{userId}")
    boolean editPassword(String password, Integer userId);
    //修改手机号
    @Update("update t_user set phone=#{phone} where user_id=#{userId}")
    boolean editPhone(String phone, Integer userId);
    //修改邮箱
    @Update("update t_user set email=#{email} where user_id=#{userId}")
    boolean editEmail(String email, Integer userId);
    //查询用户列表
    @Select("<script>" +
            "select u.*,ifnull(r.name,'前台用户') as role_name from t_user u left join t_role r on u.role_id=r.role_id" +
            "<where>" +
            "<if test='userName!=null'> u.username like '%' #{userName} '%'</if>" +
            "</where>" +
            "</script>")
    List<UserEntity> findUserList(String userName);
    //添加用户-2.保存用户信息
    @Insert("insert into t_user values(null,#{username},#{password},#{name},#{sex},#{birthday},#{phone},#{email},#{address},#{head_pic},#{status},#{role_id},#{card_code},#{user_type},7,now(),now(),#{card_type},1)")
    boolean addUser(UserEntity user);
    //显示编辑用户页面,查询选用的用户信息
    @Select("select * from t_user where user_id=#{userId}")
    UserEntity findUserById(int userId);
    //保存修改的用户信息
    @Update("update t_user set username=#{username},password=#{password},head_pic=#{head_pic},role_id=#{role_id},phone=#{phone},email=#{email},birthday=#{birthday},card_code=#{card_code},address=#{address},user_type=#{user_type},card_type=#{card_type},sex=#{sex},status=#{status},update_time=now()" +
            "where user_id=#{user_id}")
    boolean editUser(UserEntity user);

    //删除用户，物理删除，实际应该是逻辑删除
    @Update("update t_user set status=3 where user_id=#{user_id}")
    boolean deleteUser(int userId);
}
