package com.employment.filter;

import com.alibaba.fastjson.JSON;
import com.employment.constant.RedisConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.pojo.vo.LoginUserVo;
import com.employment.result.ResponseResult;
import com.employment.utils.JwtUtil;
import com.employment.utils.RedisUtil;
import com.employment.utils.WebUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: StudentEmploymentSystem
 * @ClassName JwtAuthenticationTokenFilter
 * @author: c9noo
 * @create: 2023-11-08 19:57
 * @Version 1.0
 * 在security过滤链中共享SecurityContext对象，原本是默认login方法的事情，但是我们重写了这个方法所以需要我们来做
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader(jwtUtil.getJwtName());

        // 如果token不为空
        if (StringUtils.hasText(token)) {
            try {
                Claims claims = jwtUtil.parseJWT(token);

                // claims中有数据
                String id = claims.getSubject();

                // 从redis中获取到id对应的信息
                LoginUserVo loginUserVo = redisUtil.getCacheObject(RedisConstant.REDIS_USER_KEY + id);

                // 如果没有获取到
                if (Objects.isNull(loginUserVo)) {
                    // 设置响应的内容
                    ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                    // 进行序列化并且手动封装回去
                    WebUtil.renderString(response, JSON.toJSONString(responseResult));
                    return;
                }

                // 添加到SecurityContextHolder中
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(loginUserVo, null, null)
                );
            } catch (Exception e) {
                // 内部没有数据，返回给前端
                // 设置响应的内容
                ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                // 进行序列化并且手动封装回去
                WebUtil.renderString(response, JSON.toJSONString(responseResult));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
