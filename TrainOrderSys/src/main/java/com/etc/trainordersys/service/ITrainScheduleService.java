package com.etc.trainordersys.service;


import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;

import java.util.List;

public interface ITrainScheduleService {
    //查询火车票当天发行的所有票，价格，出发时间，到达时间
    List<TrainScheduleEntity> findSelectTicket(String goTime, String startStation, String endStation);

    //查询火车票的价格
    List<ScheduleSeatInfoEntity> findSelectTicketPrices(String goTime, String startStation, String endStation);
}
