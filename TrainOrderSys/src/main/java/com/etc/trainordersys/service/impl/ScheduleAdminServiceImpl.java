package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.StationEntity;
import com.etc.trainordersys.entity.TrainCarriageEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.mapper.ScheduleAdminMapper;
import com.etc.trainordersys.service.IScheduleAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("scheduleService")
public class ScheduleAdminServiceImpl implements IScheduleAdminService {
    @Autowired
    private ScheduleAdminMapper adminMapper;
    //显示车站列表
    @Override
    public List<TrainScheduleEntity> findAllschedulePageList() {
        return adminMapper.findAllschedulePageList();
    }

    @Override
    public List<TrainCarriageEntity> findAllCarriage() {
        return adminMapper.findAllCarriage();
    }

    @Override
    public List<StationEntity> findAllStationPageList() {
        return adminMapper.findAllStationPageList();
    }
    //保存添加车次
    @Override
    public String addSchedule(TrainScheduleEntity trainSchedule) {
        return adminMapper.addSchedule(trainSchedule)>0?"true":"fail";
    }
    //显示编辑车次页面
    @Override
    public TrainScheduleEntity findAllscheduleById(int id) {
        return adminMapper.findAllscheduleById(id);
    }
    //保存编辑车次信息
    @Override
    public String edit(TrainScheduleEntity trainSchedule) {
        return adminMapper.edit(trainSchedule)>0?"true":"fail";
    }
}
