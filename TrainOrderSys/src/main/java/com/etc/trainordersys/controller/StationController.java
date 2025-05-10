package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.MenuEntity;
import com.etc.trainordersys.entity.RailwaybureauEntity;
import com.etc.trainordersys.entity.StationEntity;
import com.etc.trainordersys.entity.TicketEntity;
import com.etc.trainordersys.service.IStationService;
import com.etc.trainordersys.service.ITicketAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@Scope
public class StationController {
    @Autowired
    @Qualifier("stationService")
    IStationService stationService;
    //显示车站列表
    @GetMapping("/system/station/list")
    public String findAllRoleList(Model model, @RequestParam(value = "station_name",required = false)String name,
                                  @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
        int pageSize=2;
        PageHelper.startPage(currentPage,pageSize);
        List<StationEntity> stationList=stationService.findAllStationPageList(name);
        PageInfo<StationEntity> pageInfo=new PageInfo<>(stationList);
        model.addAttribute("page",pageInfo);
        if (name!=null){
            model.addAttribute("StationName",name);
        }
        log.info("车站列表，stationList={}",stationList);
        return "/admin/station/list";
    }
    //显示添加车站页面
    @GetMapping("/system/station/add")
    public String showAddStationType(Model model){
        List<RailwaybureauEntity> railwayBureaus=stationService.findRailwayBureau(model);
        model.addAttribute("railwayBureau",railwayBureaus);
        return "/admin/station/add";
    }
    //保存添加车站信息
    @PostMapping("/system/station/add")
    public @ResponseBody String addStation(StationEntity station){
        return stationService.addStation(station);
    }
    //显示编辑车站信息页面
    @GetMapping("/system/station/edit/{station_id}")
    public String showEditStation(@PathVariable("station_id") int station_id,Model model){
        List<RailwaybureauEntity> railwayBureaus=stationService.findRailwayBureau(model);
        model.addAttribute("railwayBureau",railwayBureaus);
        stationService.findRailwayBureau(model);
        StationEntity station=stationService.findStationById(station_id);
        model.addAttribute("station",station);
        return "/admin/station/edit";
    }
}
