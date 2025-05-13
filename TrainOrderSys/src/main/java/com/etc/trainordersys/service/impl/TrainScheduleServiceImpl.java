package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.entity.TrainStationEntity;
import com.etc.trainordersys.mapper.TrainScheduleMapper;
import com.etc.trainordersys.service.ITrainScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service("ScheduleService")
public class TrainScheduleServiceImpl implements ITrainScheduleService {

    @Autowired
    private TrainScheduleMapper trainScheduleMapper;

    //出发地，开始时间，目的地，去查询火车时刻表 ：TrainScheduleEntity
    @Override
    public List<TrainScheduleEntity> findtrainScheduleList(String start_station, String end_station, String go_time) {
        //删除前端传来的数据的一个字符
        char station_name='市';
        String start_station_now=start_station.replace(String.valueOf(station_name),"");
        String end_station_now=end_station.replace(String.valueOf(station_name),"");
        return trainScheduleMapper.findtrainScheduleList(start_station_now,end_station_now,go_time);
    }

    //查询经过站点
    @Override
    public List<TrainStationEntity> findTrainStations(String trainNumber) {
        return trainScheduleMapper.findTrainStations(trainNumber);
    }


}
