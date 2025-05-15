package com.etc.trainordersys.mapper;
import com.etc.trainordersys.entity.TrainCarriageEntity;
import com.etc.trainordersys.entity.TrainEntity;
import com.etc.trainordersys.entity.TrainTypeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrainMapper {
    //查询车辆类型
    @Select("select * from t_train")
    @Results({
            @Result(column = "train_type",
            property = "trainType",
            javaType = TrainTypeEntity.class,
            one = @One(select = "com.etc.trainordersys.mapper.TrainMapper.findType"))
    })
    List<TrainEntity> findAllTrainPageList();
    @Select("select * from t_train_type where type_id=#{type_id}")
    TrainTypeEntity findType(int type_id);
    //保存添加列车
    @Insert("insert into t_train values(null,#{train.train_code},#{train.train_type},#{carriagesNum},#{train.status},now(),now(),1)")
    boolean addTrain(TrainEntity train, Integer carriagesNum);
    //将数据插入到车厢表中
    @Insert("insert into t_train_carriage values(null,#{carriage.carriage_number},#{carriage.train_code},#{carriage.carriage_type},#{carriage.seat_nums},#{carriage.emp_seats},now(),now())")
    void addCarriages(TrainCarriageEntity carriage);
}
