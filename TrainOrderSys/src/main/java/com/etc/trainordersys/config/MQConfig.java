package com.etc.trainordersys.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbirMQ配置类
 */
@Configuration
public class MQConfig {
    //队列名称
    public static  final String TICKET_QUEUE="ticket_queue";
    //创建一个名字为ticket_queue的队列
    @Bean
    public Queue createSeckillQueue(){
        return  new Queue(TICKET_QUEUE);
    }
}
