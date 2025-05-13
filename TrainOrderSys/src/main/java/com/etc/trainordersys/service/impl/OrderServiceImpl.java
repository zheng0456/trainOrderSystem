package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.*;
import com.etc.trainordersys.mapper.OrderMapper;
import com.etc.trainordersys.service.IOrderService;
import com.etc.trainordersys.utils.RedisUtils;
import com.etc.trainordersys.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    //雪花算法工具类
    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;




    //查询用户订单下的买了几张票
    @Override
    public int ticketQuantitys(int userId, String trainNumber, String departureDate) {
        return orderMapper.ticketQuantitys(userId,trainNumber,departureDate);
    }

    //查询用户购买的票是那个火车，那个日期，那个座位类型的id
    @Override
    public int selectSeatListId(String trainNumber, int[] seatTypeId) {
        return orderMapper.selectSeatListId(trainNumber,seatTypeId);
    }

    //减库存，下订单（查询数据到订单表和订单详情表中）
    @Override
    @Transactional
    public void doSeckill(TrainOrderEntity train) {
        //获取库存
        int stock=train.getRemain_nums();
        if(stock>0){  //库存大于0 就下单
            creatOrder(train);   //创建订单  下单操作
        }else {//抢票失败  保存记录在Redis中
            setOrder(train.getUserId(),train.getTrainSchedule(),train.getSeatInfoDTO());
        }
    }

    //创建订单
    @Transactional
    public void creatOrder(TrainOrderEntity train){
        //使用雪花算法创建订单编号
        String orderNum=String.valueOf(snowflakeIdWorker.nextId());
        //创建下单
        OrderEntity order=new OrderEntity(orderNum, train.getTrainName(),train.getDeparture_date(), train.getUserId());
        //插入数据
        boolean res=orderMapper.insertOrderInfo(order);
        if (res){
            //循环遍历插入用户购票信息在 ticket的表中
            for (int i=0;i<train.getSeatInfoDTO().getPassengerName().length;i++){
                //插入用户购票信息
                orderMapper.insertTicksInfo(orderNum,train.getTrainName(),train.getTrainSchedule().getStart_station(),train.getTrainSchedule().getEnd_station(),train.getTrainSchedule().getDeparture_time(),train.getTrainSchedule().getArrive_time(),train.getTrainSchedule().getDeparture_date(),train.getSeatInfoDTO().getPassengerName()[i],train.getSeatInfoDTO().getCardType()[i],train.getSeatInfoDTO().getCardCode()[i],train.getSeatInfoDTO().getTicketType()[i],train.getSeat_type_id()[i],train.getSeatInfoDTO().getTicketPrice()[i]);
            }
            //更新用户订单信息中的购买票数
            int num=train.getSeatInfoDTO().getPassengerName().length;
            orderMapper.updateTicketQuantity(num,orderNum);
            for (int i=0;i<train.getSeatInfoDTO().getPassengerName().length;i++){
                //更新票的库存
                orderMapper.updateRemainNums(train.getTrainName(),train.getSeat_type_id()[i]);
            }
            redisUtils.set("orderInfo:",train.getOrderNum()+":"+train.getUserId(),order,0);
        }
        else {
            //秒杀失败，保存redis中一条记录，获取秒杀结果的时候获取此记录
            setOrder(train.getUserId(), train.getTrainSchedule(),train.getSeatInfoDTO());
        }
    }

    @Autowired
    RedisUtils redisUtils;
    public void setOrder(Integer userId, TrainScheduleEntity train, SeatInfoDTO seatInfo){
        redisUtils.set("foodOver:",userId+":"+train.getTrain_number(),true,0);
    }
}
