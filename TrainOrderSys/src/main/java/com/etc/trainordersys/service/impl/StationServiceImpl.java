package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.RailwaybureauEntity;
import com.etc.trainordersys.entity.StationEntity;
import com.etc.trainordersys.mapper.StationMapper;
import com.etc.trainordersys.service.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stationService")

public class StationServiceImpl implements IStationService {
    @Autowired
    private StationMapper stationMapper;
    //显示车站列表
    @Override
    public List<StationEntity> findAllStationPageList(String name) {
        return stationMapper.findAllStationPageList(name);
    }
    //显示添加车站页面
    @Override
    public List<RailwaybureauEntity> findRailwayBureau() {
        return stationMapper.findRailwayBureau();
    }
    //保存添加车站信息
    @Override
    public String addStation(StationEntity station) {
        return stationMapper.addStation(station)>0?"true":"fail";
    }
}
