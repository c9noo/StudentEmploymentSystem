package com.employment.utils;

import com.employment.pojo.vo.LoginUserVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @program: StudentEmploymentSystem
 * @ClassName SecurityUtil
 * @author: c9noo
 * @create: 2023-11-09 08:39
 * @Version 1.0
 * Security相关的工具类
 **/
public class SecurityUtil {

    /**
     * @return 获取Authentication对象
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * @return 获取到LoginUserVo
     */
    public static LoginUserVo getLoginUserVo(){
        return (LoginUserVo)getAuthentication().getPrincipal();
    }

    /**
     * @return 获取到userId
     */
    public static Long getUserId(){
        return getLoginUserVo().getUser().getId();
    }

}
