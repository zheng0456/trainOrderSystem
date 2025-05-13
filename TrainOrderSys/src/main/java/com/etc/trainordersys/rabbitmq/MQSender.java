package com.etc.trainordersys.rabbitmq;


import com.etc.trainordersys.config.MQConfig;
import com.etc.trainordersys.entity.TrainOrderEntity;
import com.etc.trainordersys.utils.RedisUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 投递消息的生产者
 */
@Service
public class MQSender {
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void sendTrainOrderMessage(TrainOrderEntity trainOrder){
        //把对象转换为json字符串格式
        String msg= RedisUtils.beanToString(trainOrder);
        //生产者将消息投递到RabbitMQ中名字为TICKET_QUEUE的队列中
        rabbitTemplate.convertAndSend(MQConfig.TICKET_QUEUE,msg);
    }
}
