package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.TrainAddDTO;
import com.etc.trainordersys.entity.TrainCarriageEntity;
import com.etc.trainordersys.entity.TrainEntity;

import java.util.List;

public interface ITrainService {
    //查询车辆类型
    List<TrainEntity> findAllTrainPageList();
    //保存添加列车
    boolean addTrain(TrainAddDTO trainAdd);


}
