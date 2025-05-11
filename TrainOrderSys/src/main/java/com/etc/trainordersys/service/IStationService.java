package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.MenuEntity;
import com.etc.trainordersys.entity.RailwaybureauEntity;
import com.etc.trainordersys.entity.StationEntity;
import org.springframework.ui.Model;

import java.util.List;

public interface IStationService {
    //显示车站列表
    List<StationEntity> findAllStationPageList(String name);

    //显示添加车站页面
    List<RailwaybureauEntity> findRailwayBureau(Model model);
    //保存添加车站信息
    String addStation(StationEntity station);

    //根据车站id显示编辑页面
    StationEntity findStationById(int stationId);
}
