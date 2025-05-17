package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.*;
import com.etc.trainordersys.service.ITrainService;
import com.etc.trainordersys.service.ITrainTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
public class TrainController {
    @Autowired
    @Qualifier("trainService")
    ITrainService trainService;
    //查询车辆类型
    @GetMapping("/system/train/list")
    public String findAllTrainList(Model model, @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
        int pageSize=2;
        PageHelper.startPage(currentPage,pageSize);
        List<TrainEntity> trainList=trainService.findAllTrainPageList();
        PageInfo<TrainEntity> pageInfo=new PageInfo<>(trainList);
        model.addAttribute("page",pageInfo);
        log.info("火车类型列表，trainList={}",trainList);
        return "/admin/train/list";
    }
    //显示添加页面
    @Autowired
    @Qualifier("TypeService")
    ITrainTypeService typeService;
    @GetMapping("/system/train/add")
    public String showAddTrain(Model model){
        List<TrainTypeEntity> trainType=typeService.findAllTypePageList();
        model.addAttribute("trainType",trainType);
        return "/admin/train/add";
    }
    //保存添加列车
    @PostMapping("/system/train/add")
    public @ResponseBody boolean addTrain(@RequestBody TrainAddDTO trainAdd){
        boolean res = trainService.addTrain(trainAdd);
        if (res){
            return true;
        }
        return  false;
    }
    //编辑列车--1.查询选中列车的信息
    @RequestMapping("system/train/showEdit/{id}")
    public String showEdit(@PathVariable("id") Integer id, Model model){
        //查询t_train表
        TrainEntity train = trainService.showEdit(id);
        //查询列车类型列表
        List<TrainTypeEntity> trainType=typeService.findAllTypePageList();
        model.addAttribute("trainType",trainType);
        model.addAttribute("train",train);
        return "/admin/train/edit";
    }
    //修改列车信息
    @PutMapping("/system/train/edit")
    public @ResponseBody String editTrain(@Validated TrainEntity train){
        boolean res = trainService.editTrain(train);
        if (res){
            return "true";
        }
        return "fail";
    }
}
