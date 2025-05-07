package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.TicketEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TicketAdminMapper {
    //显示车票列表
    @Select(" <script> " +
            " select * from t_ticket "+
            " <where> " +
            " <if test='name!=null'> name like '%' #{name} '%' </if> "+
            " </where>" +
            " </script> ")
    List<TicketEntity> findAllTicketPageList(String name);
}
