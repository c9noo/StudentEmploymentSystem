package com.employment.handler.security;

import com.alibaba.fastjson.JSON;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.result.ResponseResult;
import com.employment.utils.WebUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AccessDeniedHandlerImpl
 * @author: c9noo
 * @create: 2023-11-10 16:50
 * @Version 1.0
 * 当用户已经登录但是没有权限访问的时候
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //打印堆栈跟踪信息，方便调试
        accessDeniedException.printStackTrace();
        //自定义错误信息和状态码
        ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtil.renderString(response, JSON.toJSONString(responseResult));
    }
}
