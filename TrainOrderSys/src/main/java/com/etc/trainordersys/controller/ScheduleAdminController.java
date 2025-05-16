package com.etc.trainordersys.controller;
import com.etc.trainordersys.entity.StationEntity;
import com.etc.trainordersys.entity.TrainCarriageEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.service.IScheduleAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ScheduleAdminController {
    @Autowired
    @Qualifier("scheduleService")
    IScheduleAdminService scheduleAdminService;
    //显示车站列表
    @GetMapping("/system/train/train_schedule_list")
    public String findAllRoleList(Model model, @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
        int pageSize=2;
        PageHelper.startPage(currentPage,pageSize);
        List<TrainScheduleEntity> scheduleList=scheduleAdminService.findAllschedulePageList();
        PageInfo<TrainScheduleEntity> pageInfo=new PageInfo<>(scheduleList);
        model.addAttribute("page",pageInfo);
        return "/admin/train/train_schedule_list";
    }
    //显示添加车次页面
    @GetMapping("/system/train/train_schedule_add")
    public String showAddStationType(Model model){
        List<TrainCarriageEntity> train=scheduleAdminService.findAllCarriage();
        model.addAttribute("train",train);
        List<StationEntity> station=scheduleAdminService.findAllStationPageList();
        model.addAttribute("station",station);
        return "/admin/train/train_schedule_add";
    }
    //保存添加车次
    @PostMapping("/system/train/train_schedule_add")
    public @ResponseBody String addSchedule(TrainScheduleEntity trainSchedule){
        return scheduleAdminService.addSchedule(trainSchedule);
    }
    //显示编辑车次页面
    @GetMapping("/system/train/train_schedule_showEdit/{id}")
    public String showEdit(@PathVariable("id")int id,Model model){
        TrainScheduleEntity trainSchedule=scheduleAdminService.findAllscheduleById(id);
        model.addAttribute("trainSchedule",trainSchedule);
        List<StationEntity> station=scheduleAdminService.findAllStationPageList();
        model.addAttribute("station",station);
        return "/admin/train/train_schedule_edit";
    }
    //保存编辑车次信息
    @PutMapping ("/system/train/train_schedule_showEdit")
    public @ResponseBody String editSchedule(TrainScheduleEntity trainSchedule){
        return scheduleAdminService.edit(trainSchedule);
    }
    //
}
