package com.etc.trainordersys.controller;

import com.etc.trainordersys.entity.RoleEntity;
import com.etc.trainordersys.entity.TicketEntity;
import com.etc.trainordersys.service.ITicketAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@Scope
public class TicketAdminController {
    @Autowired
    @Qualifier("ticketAdminService")
    ITicketAdminService ticketAdminService;
    //显示车票列表
    @GetMapping("/system/ticket/list")
    public String findAllRoleList(Model model, @RequestParam(value = "name",required = false)String name,
                                  @RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage){
        int pageSize=2;
        PageHelper.startPage(currentPage,pageSize);
        List<TicketEntity> ticketList=ticketAdminService.findAllTicketPageList(name);
        PageInfo<TicketEntity> pageInfo=new PageInfo<>(ticketList);
        model.addAttribute("page",pageInfo);
        if (name!=null){
            model.addAttribute("ticketName",name);
        }
        log.info("车票列表，ticketList={}",ticketList);
        return "/admin/ticket/list";
    }
}
