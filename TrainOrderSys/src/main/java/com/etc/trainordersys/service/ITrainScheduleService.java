package com.etc.trainordersys.service;


import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ITrainScheduleService {

    //出发地，开始时间，目的地，去查询火车时刻表 ：TrainScheduleEntity
    List<TrainScheduleEntity> findtrainScheduleList(String start_station, String end_station, String go_time);

    //显示车次信息
    List<TrainScheduleEntity> findAllSchedulePageList(String name);
}
