package com.etc.trainordersys.service;


import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;

import java.util.List;

public interface ITrainScheduleService {

    //查询火车时刻表信息   出发地:cityChoice  目的地:cityChoice1  出发时间：start
    List<TrainScheduleEntity> findTrainSchedule(String cityChoice, String cityChoice1, String start);

    //查询火车的座位和票价
    List<ScheduleSeatInfoEntity> findScheduleSeatInfo(String trainName);
}
