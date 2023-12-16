package com.employment.service.impl;

import com.employment.constant.StatusConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.mapper.MenuMapper;
import com.employment.mapper.UserMapper;
import com.employment.pojo.entity.User;
import com.employment.pojo.vo.LoginUserVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @program: StudentEmploymentSystem
 * @ClassName UserDetailsServiceImpl
 * @author: c9noo
 * @create: 2023-11-06 18:13
 * @Version 1.0
 * 自定义SpringSecurity的查询方式
 **/
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;


    /**
     * 自定义查询方式，从mysql中获取数据
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //通过用户名获取用户信息
        User user = userMapper.getUserByUsername(username)
                //如果user为空
                .orElseThrow(() -> new AppSystemException(AppHttpCodeEnum.LOGIN_ERROR));

        //判断用户状态，如果不为正常返回异常
        if (StatusConstant.DISABLE.equals(user.getStatus().toString())){
            throw new AppSystemException(AppHttpCodeEnum.USER_STATUS_ERROR);
        }
        //查询用户对应的权限
        List<String> permits = menuMapper.getPermisByUserId(user.getId());
        //返回userDetails对象
        return new LoginUserVo(user,permits);
    }
}
