package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.TrainTypeEntity;
import com.etc.trainordersys.mapper.TrainTypeMapper;
import com.etc.trainordersys.service.ITrainTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TypeService")
public class TrainTypeServiceImpl implements ITrainTypeService {
    @Autowired
    private TrainTypeMapper typeMapper;
    //查询车站类型列表
    @Override
    public List<TrainTypeEntity> findAllTypePageList() {
        return typeMapper.findAllTypePageList();
    }
    //保存车站类型信息
    @Override
    public String addTrainType(TrainTypeEntity trainType) {
        return typeMapper.addTrainType(trainType)>0?"true":"fail";
    }
    //显示编辑车站类型信息界面
    @Override
    public TrainTypeEntity findTypeById(int typeId) {
        return typeMapper.findTypeById(typeId);
    }
    //保存编辑车站类型信息
    @Override
    public String editTrainType(TrainTypeEntity trainType) {
        return typeMapper.editTrainType(trainType)>0?"true":"fail";
    }
    //删除车站类型信息
    @Override
    public String deleteType(int typeId) {
        return typeMapper.deleteType(typeId)>0?"success":"fail";
    }
}
