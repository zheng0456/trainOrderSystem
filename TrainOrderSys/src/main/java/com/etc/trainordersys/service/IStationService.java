package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.RailwaybureauEntity;
import com.etc.trainordersys.entity.StationEntity;

import java.util.List;

public interface IStationService {
    //显示车站列表
    List<StationEntity> findAllStationPageList(String name);

    //显示添加车站页面
    List<RailwaybureauEntity> findRailwayBureau();
    //保存添加车站信息
    String addStation(StationEntity station);
}
