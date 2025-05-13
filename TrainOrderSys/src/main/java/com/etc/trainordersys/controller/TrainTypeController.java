package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.entity.TrainTypeEntity;
import com.etc.trainordersys.service.ITrainTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@Slf4j
public class TrainTypeController {
    @Autowired
    @Qualifier("TypeService")
    ITrainTypeService typeService;
    //查询车站类型列表
    @GetMapping("/system/train/train_type_list")
    public String findAllTypeList(Model model, @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
    int pageSize=2;
    PageHelper.startPage(currentPage,pageSize);
    List<TrainTypeEntity> typeList=typeService.findAllTypePageList();
    PageInfo<TrainTypeEntity> pageInfo=new PageInfo<>(typeList);
    model.addAttribute("page",pageInfo);
    log.info("类型列表，typeList={}",typeList);
    return "/admin/train/train_type_list";
    }
    //显示添加车站类型页面
    @GetMapping("/system/train/train_type_add")
    public String showAddTrainType(){
        return "/admin/train/train_type_add";
    }
    //保存车站类型信息
    @PostMapping("/system/train/train_type_add")
    public @ResponseBody String addTrainType(TrainTypeEntity trainType){
        return typeService.addTrainType(trainType);
    }
    //显示编辑车站类型信息界面
    @GetMapping("/system/train/train_type_edit/{type_id}")
    public String showEditType(@PathVariable("type_id")int type_id,Model model){
        TrainTypeEntity trainType=typeService.findTypeById(type_id);
        model.addAttribute("trainType",trainType);
        return "/admin/train/train_type_edit";
    }
    //保存编辑车站类型信息
    @PutMapping("/system/train/train_type_edit")
    public @ResponseBody String editTrainType(TrainTypeEntity trainType){
        return typeService.editTrainType(trainType);
    }
    //删除车站类型信息
    @DeleteMapping("/system/train/deleteType")
    public @ResponseBody String deleteType(@RequestParam("id")int type_id){
        String res=typeService.deleteType(type_id);
        return res;
    }
}
