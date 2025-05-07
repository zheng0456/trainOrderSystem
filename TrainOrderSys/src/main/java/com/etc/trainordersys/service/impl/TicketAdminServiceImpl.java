package com.etc.trainordersys.service.impl;

import com.etc.trainordersys.entity.TicketEntity;
import com.etc.trainordersys.mapper.TicketAdminMapper;
import com.etc.trainordersys.service.ITicketAdminService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ticketAdminService")
public class TicketAdminServiceImpl implements ITicketAdminService {
    @Autowired
    private TicketAdminMapper ticketAdminMapper;

    //显示车票列表
    @Override
    public List<TicketEntity> findAllTicketPageList(String name) {
        return ticketAdminMapper.findAllTicketPageList(name);
    }
}
