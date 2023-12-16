package com.employment.exception;

import com.employment.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: StudentEmploymentSystem
 * @ClassName SystemException
 * @author: c9noo
 * @create: 2023-11-06 18:25
 * @Version 1.0
 * 自定义异常类，用于封装系统中出现的异常信息
 **/
@Slf4j
public class AppSystemException extends RuntimeException{
    private int code;
    private String msg;
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public AppSystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
    public AppSystemException(AppHttpCodeEnum httpCodeEnum, String errorMessage) {
        super(errorMessage != null ? errorMessage : httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = errorMessage != null ? errorMessage : httpCodeEnum.getMsg();
    }
}
