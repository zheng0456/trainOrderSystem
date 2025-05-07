package com.etc.trainordersys.entity;

/**
 * (1)枚举类型:可以写枚举值、成员变量、构造方法、方法
 */
public enum ResultEnum {
    SUCCESS(200,"成功"),FAIL(500,"失败"),
    LOGIN_NAME_ERROR(5001,"用户名错误"),
    LOGIN_PWD_ERROR(5002,"密码错误"),
    REPEATED_SECKILL(5003,"重复秒杀"),
    UNDER_STOCK(5004,"库存不足，秒光了"),
    REPEATED_SUBMIT(5005,"重复提交"),
    SYSTEM_BUSY(5006,"系统繁忙"),
    REQUEST_BUSY(5007,"请求繁忙"),
    SECKILL_OVER(5008,"秒杀结束");

    //枚举值
    //成员变量
    private int code;  //状态码
    private String message; //状态描述

    //构造方法
    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    //方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
