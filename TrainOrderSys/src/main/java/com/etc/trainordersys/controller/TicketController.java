package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.OrderEntity;
import com.etc.trainordersys.entity.PassengerEntity;
import com.etc.trainordersys.entity.TrainScheduleEntity;
import com.etc.trainordersys.entity.UserEntity;
import com.etc.trainordersys.service.ITicketService;
import com.etc.trainordersys.utils.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class TicketController {
    @Autowired
    @Qualifier("ticketService")
    ITicketService ticketService;

    //预定票判断预定前是否 在本账号下是否有订单存在未支付
    @GetMapping("/view/ticketing/findUnPayOrder")
    public @ResponseBody boolean findUnPayOrder(HttpSession session){
        int userId=((UserEntity)session.getAttribute("user")).getUser_id();
        //查询订单信息看用户是否有订单未支付
        boolean res =ticketService.findUserPayOrder(userId);
        //如果有订单为支付返回fail  没有返回true
        return res;
    }

    @Autowired
    private RedisUtils redisUtils;   //引入工具类
    //预定票 预定票点击的火车车次信息
    @RequestMapping("/view/ticketing/confirmPassenger")
    public String confirmPassenger( String trainInfo,Model model,HttpSession session){
        //从session中获取用户ID
        int userId=((UserEntity)session.getAttribute("user")).getUser_id();
        //类型转换为实体类
        TrainScheduleEntity train=redisUtils.stringToBean(trainInfo,TrainScheduleEntity.class);
        //查询该用户下的乘车人信息
        List<PassengerEntity> passengers=ticketService.findUserPasseragesInformation(userId);
        model.addAttribute("passengers",passengers);
        model.addAttribute("trainInfos",train);  //保存数据到model中
        return "/home/order/choose_passenger";
    }

    //用户购票后选座
    @RequestMapping("/view/ticketing/checkTicketInfo")
    public String checkTicketInfo(@RequestParam("departure_date") String departure_date, @RequestParam("departure_time") String departure_time,
                                  @RequestParam("train_number") String train_number,@RequestParam("start_station") String start_station,
                                  @RequestParam("end_station") String end_station,@RequestParam("arrive_time") String arrive_time,
                                  @RequestParam("id") Integer id,@RequestParam("sort") Integer[] sort,@RequestParam("ticket_type") Integer [] ticket_type,
                                  @RequestParam("seat_type")String [] seat_type,@RequestParam("passenger_name") String[]passenger_name,
                                  @RequestParam("card_type")String []card_type,@RequestParam("card_code")String [] card_code,Model model,HttpSession session){
        //添加订票信息
        int res=ticketService.addTickets(departure_date,departure_time,train_number,start_station,end_station,arrive_time,sort,ticket_type,seat_type,passenger_name,card_type,card_code);
        if(res!=0){
            //判断用户选的座位类型
            int flagSeat=ticketService.findFlagSeat(sort,seat_type);
            //查询火车列表
            TrainScheduleEntity trainInfo=ticketService.findTrainInfoList(id);
            //查询用户下的乘车人信息
            int userId=((UserEntity) session.getAttribute("user")).getUser_id();
            List<PassengerEntity>passengers=ticketService.findUserPasseragesInformation(userId);
            //保存用户选的座位类型，返回前端
            model.addAttribute("flagSeat",flagSeat);
            //保存火车信息
            model.addAttribute("trainInfo",trainInfo);
        }
        //跳转页面到选座页面
        return "/home/order/choose_seat";
    }
}
