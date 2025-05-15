package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.TrainAddDTO;
import com.etc.trainordersys.entity.TrainTypeEntity;

import java.util.List;

public interface ITrainTypeService {
    //查询车站类型列表
    List<TrainTypeEntity> findAllTypePageList();
    //保存车站类型信息
    String addTrainType(TrainTypeEntity trainType);
    //显示编辑车站类型信息界面
    TrainTypeEntity findTypeById(int typeId);
    //保存编辑车站类型信息
    String editTrainType(TrainTypeEntity trainType);
    //删除车站类型信息
    String deleteType(int typeId);
}
