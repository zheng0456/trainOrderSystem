package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.TrainTypeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrainTypeMapper {
    //查询车站类型列表
    @Select("select * from t_train_type")
    List<TrainTypeEntity> findAllTypePageList();
    //保存车站类型信息
    @Insert("insert into t_train_type(name,status,max_speed,avg_speed,create_time,update_time) values(#{name},#{status},#{max_speed},#{avg_speed},now(),now())")
    int addTrainType(TrainTypeEntity trainType);
    //显示编辑车站类型信息界面
    @Select("select * from t_train_type where type_id=#{typeId}")
    TrainTypeEntity findTypeById(int typeId);
    //保存编辑车站类型信息
    @Update("update t_train_type set name=#{name},status=#{status},max_speed=#{max_speed},avg_speed=#{avg_speed},update_time=now()" +
            "where type_id=#{type_id}")
    int editTrainType(TrainTypeEntity trainType);
    //删除车站类型信息
    @Delete("delete from t_train_type where type_id=#{typeId}")
    int deleteType(int typeId);
}
