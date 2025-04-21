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

    //查询火车票当天发行的所有票，价格，出发时间，到达时间
    @Override
    public List<TrainScheduleEntity> findSelectTicket(String goTime, String startStation, String endStation) {
        return trainScheduleMapper.findSelectTicket(goTime,startStation,endStation);
    }

    //查询火车票的价格
    @Override
    public List<ScheduleSeatInfoEntity> findSelectTicketPrices(String goTime, String startStation, String endStation) {
        return trainScheduleMapper.findSelectTicketPrices(goTime,startStation,endStation);
    }
}
