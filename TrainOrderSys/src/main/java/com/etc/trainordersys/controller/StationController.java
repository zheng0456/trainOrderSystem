package com.etc.trainordersys.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String findAllRoleList(Model model, @RequestParam(value = "name",required = false)String name,
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
        List<RailwaybureauEntity> railwayBureaus=stationService.findRailwayBureau();
        model.addAttribute("railwayBureau",railwayBureaus);
        return "/admin/station/add";
    }
}
