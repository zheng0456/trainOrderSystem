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

    @Override
    public List<StationEntity> findAllStationPageList(String name) {
        return stationMapper.findAllStationPageList(name);
    }

    @Override
    public List<RailwaybureauEntity> findRailwayBureau() {
        return stationMapper.findRailwayBureau();
    }
}
