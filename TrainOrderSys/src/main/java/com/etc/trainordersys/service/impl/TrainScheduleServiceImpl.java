package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.mapper.TrainScheduleMapper;
import com.etc.trainordersys.service.ITrainScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ScheduleService")
public class TrainScheduleServiceImpl implements ITrainScheduleService {

    @Autowired
    private TrainScheduleMapper trainScheduleMapper;
    //查询火车时刻表信息
    @Override
    public List<TrainScheduleEntity> findTrainSchedule(String cityChoice, String cityChoice1, String start) {
        return trainScheduleMapper.findTrainSchedule(cityChoice,cityChoice1,start);
    }

    //查询火车的座位和票价
    @Override
    public List<ScheduleSeatInfoEntity> findScheduleSeatInfo(String trainName) {
        return trainScheduleMapper.findScheduleSeatInfo(trainName);
    }
}
