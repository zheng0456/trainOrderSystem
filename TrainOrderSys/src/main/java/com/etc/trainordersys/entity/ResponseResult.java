package com.etc.trainordersys.entity;

import lombok.Data;

/**
 * 封装结果类
 * @param <T>
 */
@Data
public class ResponseResult<T>{

    private  int code;  //状态码  200-- 成功    404- 找不到资源
    private String msg ;  //信息
    private T data;  //内容

    //成功
    public ResponseResult(T data) {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg =  ResultEnum.SUCCESS.getMessage();
        this.data = data;
    }
    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //成功调用---查询
    public static <T> ResponseResult<T> success(T data){
        return  new ResponseResult<>(data);
    }
    //成功调用---增删该
    public static <T> ResponseResult<T> success(){
        return  new ResponseResult<>(null);
    }
    //调用失败
    public static <T> ResponseResult<T> fail(ResultEnum resultEnum){
        return  new ResponseResult<>(resultEnum.getCode(), resultEnum.getMessage());
    }
}
