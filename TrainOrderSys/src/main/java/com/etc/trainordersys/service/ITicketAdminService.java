package com.etc.trainordersys.service;

import com.etc.trainordersys.entity.TicketEntity;

import java.util.List;

public interface ITicketAdminService {
    List<TicketEntity> findAllTicketPageList(String name);
}
