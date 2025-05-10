package com.etc.trainordersys.mapper;

import com.etc.trainordersys.entity.RailwaybureauEntity;
import com.etc.trainordersys.entity.StationEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StationMapper {
    @Select(" <script> " +
            " select * from t_station "+
            " <where> " +
            " <if test='station_name!=null'> station_name like '%' #{station_name} '%' </if> "+
            " </where>" +
            " </script> ")
    @Results({
            @Result(column = "railwayBureau_id",
                    property = "railwayBureaus",
                    javaType = RailwaybureauEntity.class,
                    many = @Many(select = "com.etc.trainordersys.mapper.StationMapper.findName"))
    })
    List<StationEntity> findAllStationPageList(String name);
    @Select("select * from t_railwaybureau where id=#{railwayBureau_id}")
    RailwaybureauEntity findName(int railwayBureau_id);


    @Select("select * from t_railwayBureau ")
    List<RailwaybureauEntity> findRailwayBureau();


    @Insert("insert into t_station(station_name,city,railwayBureau_id,phone,create_time,update_time) values(#{station_name},#{city},#{railwayBureau_id},#{phone},now(),now())")
    int addStation(StationEntity station);
    //根据车站id显示编辑页面
    @Select("select * from t_station where station_id=#{stationId}")
    StationEntity findStationById(int stationId);
}
