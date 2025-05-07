package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.TicketEntity;

import java.util.List;

public interface ITicketAdminService {
    //显示车票列表
    List<TicketEntity> findAllTicketPageList(String name);
}
