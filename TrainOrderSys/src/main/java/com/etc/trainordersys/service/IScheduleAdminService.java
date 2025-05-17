package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.StationEntity;
import com.etc.trainordersys.entity.TrainCarriageEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.entity.TrainStationEntity;

import java.util.List;

public interface IScheduleAdminService {
    //显示车站列表
    List<TrainScheduleEntity> findAllschedulePageList();
    //查询所有车厢
    List<TrainCarriageEntity> findAllCarriage();

    List<StationEntity> findAllStationPageList();
    //保存添加车次
    String addSchedule(TrainScheduleEntity trainSchedule);
    //显示编辑车次页面
    TrainScheduleEntity findAllscheduleById(int id);
    //保存编辑车次信息
    String edit(TrainScheduleEntity trainSchedule);
    //删除车次信息
    String deleteSchedule(int id);

    TrainScheduleEntity findAllschedule();
    //保存添加中间站点
    String addStation(TrainStationEntity trainStation);
}
