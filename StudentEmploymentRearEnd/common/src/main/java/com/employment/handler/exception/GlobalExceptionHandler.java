package com.employment.handler.exception;

import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @program: StudentEmploymentSystem
 * @ClassName GlobalExceptionHandler
 * @author: c9noo
 * @create: 2023-11-06 18:54
 * @Version 1.0
 * 定义统一异常处理器，进行处理我们封装的runtimeException 和 Exception
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 拦截自定义异常
     * @param appSystemException
     * @return
     */
    @ExceptionHandler(AppSystemException.class)
    public ResponseResult systemExceptionHandler(AppSystemException appSystemException){
        log.error("运行时出现了异常{}!",appSystemException);
        // 返回我们自定义的状态码、和自定义的提示信息
        return ResponseResult.errorResult(appSystemException.getCode(),appSystemException.getMsg());
    }

    //MissingServletRequestParameterException
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public ResponseResult dataException(){
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_ERROR);
    }


    /**
     * 拦截接收字段出现问题时的异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult fieldException(MethodArgumentNotValidException e){
        log.error("出现字段异常:{}",e);
        /**
         * 先获取我们自定义的状态码
         * 在通过getBindingResult获取到BindingResult对象，这个对象包含了验证错误的详细信息
         * 在通过getFieldErrors获取到所有的字段错误
         * 而我们自定义的错误信息 就在DefaultMessage中
         */
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * Exception异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("系统出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        //当用户被锁定时会走此处
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
