package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.service.ITrainScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 火车时刻表控制类
 */
@Controller
public class TrainScheduleController {
    @Autowired
    @Qualifier("ScheduleService")
    ITrainScheduleService trainScheduleService;

    //指定出发地，到达地，指定出发日期进行查询车票  主页面搜索跳转页面
    @GetMapping("/home/index")
    public String findTrainList(@RequestBody @RequestParam(value = "start_station") String start_station, @RequestParam("end_station") String end_station, @RequestParam(value = "go_time") String go_time, Model model){
        //出发地，开始时间，目的地，去查询火车时刻表 ：TrainScheduleEntity
        List<TrainScheduleEntity> trainList=trainScheduleService.findtrainScheduleList(start_station,end_station,go_time);
        //将查询出的 火车时刻表 保存到model
        model.addAttribute("trainList",trainList);
        //存入开始车站   结束车站  出发地，目的地，开始时间
        model.addAttribute("start_station",start_station);
        model.addAttribute("end_station",end_station);
        model.addAttribute("go_time",go_time);

        return "/home/show";
    }

    

}
