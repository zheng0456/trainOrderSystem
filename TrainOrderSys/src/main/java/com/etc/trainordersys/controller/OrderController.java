package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.*;
import com.etc.trainordersys.rabbitmq.MQSender;
import com.etc.trainordersys.service.IOrderService;
import com.etc.trainordersys.utils.RedisDistributedLock;
import com.etc.trainordersys.utils.RedisUtils;
import com.google.common.util.concurrent.RateLimiter;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户订单控制层
 */
@Controller
public class OrderController {
    @Autowired
    @Qualifier("orderService")
    private IOrderService orderService;

    @Autowired
    private RedisUtils redisUtils;   //引入工具类

    //内存标记，减少redis访问，存储菜品id是否秒杀结束，库存预减后，取值如果库存小于0设置此标识
    private Map<Integer, Boolean> localOverMap = new HashMap<>();
    //每秒请求一个令牌
    private static final RateLimiter rateLimiter = RateLimiter.create(1);
    @Autowired
    private RedisDistributedLock redisDistributedLock;
    @Autowired
    private MQSender mqSender;  //自动注入发送者对象
    @Transactional  //创建事务 成功一起提交，失败一起回滚
    //秒杀票
    @PostMapping("/view/ticketing/createOrder")   //ResponseResult<Map<Object,String>> 状态码实体类
    public @ResponseBody ResponseResult createOrder(@RequestParam(required = false) String trainInfo, @RequestParam(required = false) String seatInfo, HttpSession session) {
        try {
            //将前端传来数据进行转换为 火车列表实体类
            TrainInfoDTO train=redisUtils.stringToBean(trainInfo,TrainInfoDTO.class);
            //将前端传来数据进行转换为 座位实体类
            SeatInfoDTO seat = redisUtils.stringToBean(seatInfo, SeatInfoDTO.class);
            //获取用户id
            int userId = ((UserEntity) session.getAttribute("user")).getUser_id();
            //设置限流接口 每秒允许10个用户通过
            //tryAcquire:是一个非阻塞式尝试获取令牌的方法，作用：立即判断当前是否有可用令牌，二不会阻塞当前线程
            if (!rateLimiter.tryAcquire()) {
                return ResponseResult.fail(ResultEnum.REQUEST_BUSY);
            }
            //判断是否抢票结束标记 如果结束响应数据，如果没有结束，继续执行下一步
            Boolean over = localOverMap.get(train.getId());
            if (over != null && over == true) {   //true 秒杀结束   false秒杀没结束
                return ResponseResult.fail(ResultEnum.SECKILL_OVER);
            }
            //判断用户是否重复秒杀，系统把订单存入到Redis中，所以从Redis中获取订单
            RepeatedTicketsEntity repeatedTickets = redisUtils.get("orderInfo", train.getId()+ ":" + userId, RepeatedTicketsEntity.class);
            if (repeatedTickets != null) {
                return ResponseResult.fail(ResultEnum.REPEATED_SUBMIT);
            }
            //查询用户订单下的买了几张票
            int pay_tickets = orderService.ticketQuantitys(userId, train.getTrain_number(), train.getDeparture_date());
            //查询用户购买的票是那个火车，那个日期，那个座位类型的id
            int seatListId = orderService.selectSeatListId(train.getTrain_number(), seat.getSeatTypeId());
            //循环遍历用户买票数量来进行库存自减
            for (int i = 0; i < pay_tickets; i++) {
                //4.Redis中进行库存预减，如果redis中获取到的库存小于0则存储结束标记并响应数据
                long tickets = redisUtils.decr("remain_nums",":"+seatListId);
                if (tickets < 0) {
                    localOverMap.put(seatListId, true);  //内存标记秒杀结束
                    return ResponseResult.fail(ResultEnum.UNDER_STOCK);
                }
            }
            //获取分布式锁防止并发重复提交（分布式锁+Lua脚本），如果重复提交直接响应数据，没有则执行下一步
            String lockKey = "seckill:lock" + seatListId + ":" + userId;
            try {
                if (!redisDistributedLock.tryLock(lockKey, 60, userId)) {
                    return ResponseResult.fail(ResultEnum.REPEATED_SUBMIT);
                }
                //将用户下单信息信息放入到RabbitMQ消息队列中,队列监听，监听后处理订单
                TrainOrderEntity trainOrder=new TrainOrderEntity();
                trainOrder.setTrainName(train.getTrain_number());
                trainOrder.setUserId(userId);
                trainOrder.setDeparture_date(train.getDeparture_date());
                for (int i=0;i<pay_tickets;i++){
                    trainOrder.setSeatInfoDTO(seat);
                    trainOrder.setSeat_type_id(seat.getSeatTypeId());
                    mqSender.sendTrainOrderMessage(trainOrder);//入队，把秒杀到的菜品对象添加到消息队列中
                }
                return ResponseResult.success();   //排队中
            } finally {
                redisDistributedLock.unLock(lockKey, userId);
            }
        } catch (Exception e) {
            return ResponseResult.fail(ResultEnum.FAIL);
        }
    }
}