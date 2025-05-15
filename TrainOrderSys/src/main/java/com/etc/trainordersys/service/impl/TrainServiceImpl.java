package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.CarriageDTO;
import com.etc.trainordersys.entity.TrainAddDTO;
import com.etc.trainordersys.entity.TrainCarriageEntity;
import com.etc.trainordersys.entity.TrainEntity;
import com.etc.trainordersys.mapper.TrainMapper;
import com.etc.trainordersys.service.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("trainService")
public class TrainServiceImpl implements ITrainService {
    @Autowired
    private TrainMapper trainMapper;
    //查询车辆类型
    @Override
    public List<TrainEntity> findAllTrainPageList() {
        return trainMapper.findAllTrainPageList();
    }
    //保存添加列车
    @Override
    @Transactional
    public boolean addTrain(TrainAddDTO trainAdd) {
        // 创建列车对象
        TrainEntity train = new TrainEntity();
        train.setTrain_code(trainAdd.getTrainCode());
        train.setTrain_type(trainAdd.getTrainType());
        train.setStatus(trainAdd.getStatus());
        //获取车厢数
        Integer carriagesNum = trainAdd.getCarriages().size();
        //将列车信息插入t_ticket表中
        boolean res = trainMapper.addTrain(train,carriagesNum);
        if (res){
            // 处理车厢数据
            if (trainAdd.getCarriages() != null && !trainAdd.getCarriages().isEmpty()) {
                List<TrainCarriageEntity> carriages = new ArrayList<>();
                for (CarriageDTO carriageDTO : trainAdd.getCarriages()) {
                    TrainCarriageEntity carriage = new TrainCarriageEntity();
                    carriage.setTrain_code(train.getTrain_code());
                    carriage.setCarriage_number(carriageDTO.getCarriageNumber());
                    carriage.setCarriage_type(carriageDTO.getCarriageType());
                    carriage.setSeat_nums(carriageDTO.getSeatCount());
                    carriage.setEmp_seats(carriageDTO.getNoSeatCount() != null ? carriageDTO.getNoSeatCount() : 0);
                    carriages.add(carriage);
                }
                for (TrainCarriageEntity carriage:carriages){
                    //将数据插入到车厢表中
                    trainMapper.addCarriages(carriage);
                }
            }
        }else {
            return false;
        }
        return true;
    }
}
