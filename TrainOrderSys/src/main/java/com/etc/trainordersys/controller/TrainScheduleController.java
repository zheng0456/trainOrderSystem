package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.ScheduleSeatInfoEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.service.ITrainScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TrainScheduleController {
    @Autowired
    @Qualifier("ScheduleService")
    ITrainScheduleService trainScheduleService;
    //指定出发地，到达地，指定出发日期进行查询车票
    @GetMapping("/home/show")
    public String select_ticket(@RequestParam(value = "cityChoice" ,required = false) String cityChoice, @RequestParam(value = "cityChoice1") String cityChoice1, @RequestParam(value = "start") String start, HttpSession session, Model model){
        //查询火车时刻表信息   出发地:cityChoice  目的地:cityChoice1  出发时间：start
        List<TrainScheduleEntity> trainList =trainScheduleService.findTrainSchedule(cityChoice,cityChoice1,start);
        session.setAttribute("TRAINLIST",trainList);   //存储火车时刻表在session
        //查询火车的座位和票价
        String trainName=((TrainScheduleEntity)session.getAttribute("TRAINLIST")).getTrain_number();  //调用session 从中调出火车名
        List<ScheduleSeatInfoEntity> scheduleSeatInfoList =trainScheduleService.findScheduleSeatInfo(trainName);
        model.addAttribute("trainList",trainList);   //保存数据  火车列表数据
        model.addAttribute("scheduleSeatInfoList",scheduleSeatInfoList);   //保存 火车票价数据
        return "/home/show";
    }
}
