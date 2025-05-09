package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.RailwaybureauEntity;
import com.etc.trainordersys.entity.StationEntity;

import java.util.List;

public interface IStationService {
    List<StationEntity> findAllStationPageList(String name);


    List<RailwaybureauEntity> findRailwayBureau();
}
