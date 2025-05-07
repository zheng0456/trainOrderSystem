package com.etc.trainordersys.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


@Controller
@Slf4j
public class CommonController {
    //通用的图片上传的方法
    @PostMapping("/uploadHeadImg")
    public @ResponseBody String uploadHeadImg(@RequestParam(value = "img") MultipartFile file, String type){
        try {
            if (!file.isEmpty()) {  //文件是否为空
                log.info("========文件上传方法入参，type={},file={}========",type,file);
                //1.上传文件,获取文件路径,最近存储图片的路径/upload/head/日期/图片名
                //(1)接收上传文件 MultipartFile file
                //(2)根据时间戳创建文件名,即使第二次上传相同的图片，也不会被覆盖
                String fileName = System.currentTimeMillis()+file.getOriginalFilename();
                //(3)日期文件夹
                String dateFileName = LocalDate.now().toString();
                //(4)存储图片的真实路径，拼接前面的文件名
                //C:\\Users\\31315\\Desktop\\upload\\headpic\\2024-09-13\\17262107777712.jpg
                String destFileName = "C:\\Users\\Lenovo\\Desktop\\upload\\"+type+"\\"+dateFileName+"\\"+fileName;
                //(5)第一次运行，文件夹往往不存在，此时需要创建文件夹
                File destFile =new File(destFileName);
                destFile.getParentFile().mkdirs(); //创建多个目录
                //(6)把浏览器上传的文件复制到指定的路径下
                file.transferTo(destFile);
                log.info("========图片上传完毕=========");
                //(7)返回路径
                return "/upload/"+type+"/"+dateFileName+"/"+fileName;
            }
        } catch (IOException e) {
            log.info("====上传业务出现异常，e={}=====",e);
        }
        return "fail";
    }
}
