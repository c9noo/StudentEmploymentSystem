package com.employment.handler.security;

import com.alibaba.fastjson.JSON;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.result.ResponseResult;
import com.employment.utils.WebUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AuthenticationEntryPointImpl
 * @author: c9noo
 * @create: 2023-11-10 17:00
 * @Version 1.0
 * 处理Security在身份认证过程中的异常
 **/
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //打印堆栈跟踪信息，方便调试
        authException.printStackTrace();
        //初始化一个ResponseResul对象
        ResponseResult responseResult = null;
        //根据不同的异常进行处理
        if(authException instanceof BadCredentialsException){
            // 如果是 BadCredentialsException，创建包含登录错误信息的 ResponseResult
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        } else if(authException instanceof InsufficientAuthenticationException){
            // 如果是 InsufficientAuthenticationException，创建包含需要重新登录信息的 ResponseResult
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            // 如果是其他类型的异常，创建包含系统错误信息的 ResponseResult
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //返回给前端
        WebUtil.renderString(response, JSON.toJSONString(responseResult));
    }
}
