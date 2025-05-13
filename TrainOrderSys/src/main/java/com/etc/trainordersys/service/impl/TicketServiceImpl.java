package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.*;
import com.etc.trainordersys.mapper.TicketMapper;
import com.etc.trainordersys.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ticketService")
public class TicketServiceImpl implements ITicketService {
    @Autowired
    private TicketMapper ticketMapper;

    //查询订单信息看用户是否有订单未支付
    @Override
    public boolean findUserPayOrder(int userId) {
        List<OrderEntity> orderEntity=ticketMapper.findUserPayOrder(userId);
        //如果返回值为0 未支付  返回为1 已支付
        for (int i=0;i<orderEntity.size();i++){
            if (orderEntity.get(i).getPayment()==1){
                return true;
            }
        }
        return false;
    }

    //查询该用户下的乘车人信息
    @Override
    public List<PassengerEntity> findUserPasseragesInformation(int userId) {
        return ticketMapper.findUserPasseragesInformation(userId);
    }


    //判断用户选的座位类型
    @Override
    public int findFlagSeat(Integer[] sort, String[] seatType) {
        int flagSeat=0;
        for (int i=0;i<sort.length;i++){
            String [] seatPars=seatType[i].split(",");   //将传过来的数据进行拆分
            int seat_type_id=Integer.parseInt(seatPars[0]);    //取出座位类型id编号
            //根据不同的类型号进行返回不同的数据
            if (seat_type_id==4){
                flagSeat=1;
            }else if (seat_type_id==5 || seat_type_id==6||seat_type_id==1){
                flagSeat=3;
            }
        }
        return flagSeat;
    }

    //查询火车信息
    @Override
    public TrainScheduleEntity findTrainInfoList(Integer id) {

        return ticketMapper.findTrainInfoList(id);
    }

    //临时添加用户购票信息
    @Override
    public List<TicketEntity> TemporaryAddTickets(Integer[] sort, Integer[] ticketType, String[] seatType1, String[] passengerName, String[] cardType, String[] cardCode) {
        List<TicketEntity> tickets=new ArrayList<>();
        int seat_type_id=0;
        double prices=0;
        String[]seatPars=new String[sort.length];
        for (int i=0;i<sort.length;i++){
            TicketEntity ticket=new TicketEntity();
            if (sort.length>1) {
                seatPars = seatType1[i].split(",");    //将传过来的数据进行拆分
                seat_type_id = Integer.parseInt(seatPars[0]);    //取出座位类型id编号
                prices = Double.parseDouble(seatPars[1]);     //取出不同座位的价格
                if (ticketType[i] == 0) {     //成人票
                    prices = prices;
                }
                if (ticketType[i] == 1) {    //儿童票
                    prices = prices * 0.5;
                }
                if (ticketType[i] == 2) {   //学生票
                    prices = prices * 0.7;
                }
                if (ticketType[i] == 3) {   //军人票
                    prices = prices * 0.75;
                }
                //遍历列表分别往进插入临时数据
                ticket.setSort(sort[i]);
                ticket.setTicket_price(prices);
                ticket.setPassenger_name(passengerName[i]);
                ticket.setCard_code(cardCode[i]);
                ticket.setCard_type(cardType[i]);
                //查询座位类型名称
                SeatTypeEntity seatType=ticketMapper.findSeatTypeNames(seat_type_id);
                ticket.setSeatType(seatType);
                ticket.setTicket_type(ticketType[i]);
                tickets.add(ticket);
            }else {
                seatType1[i].split(",");
                seat_type_id = Integer.parseInt(seatType1[0]);    //取出座位类型id编号
                prices = Double.parseDouble(seatType1[1]);     //取出不同座位的价格
                if (ticketType[i] == 0) {     //成人票
                    prices = prices;
                }
                if (ticketType[i] == 1) {    //儿童票
                    prices = prices * 0.5;
                }
                if (ticketType[i] == 2) {   //学生票
                    prices = prices * 0.7;
                }
                if (ticketType[i] == 3) {   //军人票
                    prices = prices * 0.75;
                }
                //遍历列表分别往进插入临时数据
                ticket.setSort(sort[i]);
                ticket.setTicket_price(prices);
                ticket.setPassenger_name(passengerName[i]);
                ticket.setCard_code(cardCode[i]);
                ticket.setCard_type(cardType[i]);
                //查询座位类型名称
                SeatTypeEntity seatType=ticketMapper.findSeatTypeNames(seat_type_id);
                ticket.setSeatType(seatType);
                ticket.setTicket_type(ticketType[i]);
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    //根据火车名和火车开始日期，查询火车抢票详情
    @Override
    public TrainOrderEntity findSeckillDetail(String trainName, String departureDate) {
        return ticketMapper.findSeckillDetail(trainName,departureDate);
    }
}
