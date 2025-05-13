package com.etc.trainordersys.service;


import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.entity.TrainStationEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ITrainScheduleService {

    //出发地，开始时间，目的地，去查询火车时刻表 ：TrainScheduleEntity
    List<TrainScheduleEntity> findtrainScheduleList(String start_station, String end_station, String go_time);

    //查询经过站点
    List<TrainStationEntity> findTrainStations(String trainNumber);
}
