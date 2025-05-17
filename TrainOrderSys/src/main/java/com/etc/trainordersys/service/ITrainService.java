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

    //查询t_train表
    TrainEntity showEdit(Integer id);
    //修改列车信息
    boolean editTrain(TrainEntity train);
    //删除列车信息
    String delete(int id);
}
