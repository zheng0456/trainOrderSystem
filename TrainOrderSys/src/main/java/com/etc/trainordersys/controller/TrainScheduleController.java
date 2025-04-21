package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.service.ITrainScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TrainScheduleController {
    @Autowired
    @Qualifier("ScheduleService")
    ITrainScheduleService trainScheduleService;
    //指定出发地，到达地，指定出发日期进行查询车票
    @GetMapping("/home/show")
    public @ResponseBody String select_ticket(String go_time, String start_station, String end_station, HttpSession session){
        //查询火车票当天发行的所有票，价格，出发时间，到达时间
        List<TrainScheduleEntity> tainList=trainScheduleService.findSelectTicket(go_time,start_station,end_station);
        //查询火车票的价格
        List<ScheduleSeatInfoEntity> seatInfoEntityList=trainScheduleService.findSelectTicketPrices(go_time,start_station,end_station);


        return "/home/show";
    }
}
