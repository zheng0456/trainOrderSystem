package com.etc.trainordersys.rabbitmq;


import com.etc.trainordersys.config.MQConfig;
import com.etc.trainordersys.entity.RepeatedTicketsEntity;
import com.etc.trainordersys.entity.TrainOrderEntity;
import com.etc.trainordersys.service.IOrderService;
import com.etc.trainordersys.service.ITicketService;
import com.etc.trainordersys.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ITicketService ticketService;
    @Autowired
    IOrderService orderService;
    //注册监听器，并指定消息队列的名称
    @RabbitListener(queues = MQConfig.TICKET_QUEUE)
    public void receive(String message){
        //把json字符串转换成对象
        TrainOrderEntity train =RedisUtils.stringToBean(message, TrainOrderEntity.class);
        String trainName=train.getTrainName();
        String departure_date= train.getDeparture_date();
        Integer userId = train.getUserId();
        //根据火车名和火车开始日期，查询火车抢票详情
        train=ticketService.findSeckillDetail(trainName,departure_date);
        train.setUserId(userId);
        int stock=train.getRemain_nums();  //获取剩余票数
        if (stock<0){
            return;
        }
        //判断是否已经秒杀到了
        TrainOrderEntity trainOrder = redisUtils.get("orderInfo", trainName + ":" + userId);
        if (trainOrder!=null){
            return;
        }
        //减库存，下订单（查询数据到订单表和订单详情表中）
        orderService.doSeckill(train);
    }
}
