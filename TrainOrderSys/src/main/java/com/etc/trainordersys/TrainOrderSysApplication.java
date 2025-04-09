package com.etc.trainordersys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.etc.trainordersys.mapper"})
public class TrainOrderSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainOrderSysApplication.class, args);
    }

}
