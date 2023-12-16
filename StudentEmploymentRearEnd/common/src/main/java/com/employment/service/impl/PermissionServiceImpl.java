package com.employment.service.impl;

import com.employment.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName PermissionServiceImpl
 * @author: c9noo
 * @create: 2023-11-10 16:28
 * @Version 1.0
 * 自定义权限判断，替代Security的权限判断
 **/
@Service("Permission")
public class PermissionServiceImpl {

    /**
     * 单一参数，转换为List集合，进行调用重载
     */
    public boolean hasPermission(String permission){
        return hasAnyPermission(Collections.singletonList(permission));
    }

    /**
     * 判断当前用户是否具有某些权限
     * @param permissions 需要被判断的权限列表
     * @return 没有的话返回False，有的话返回True
     */
    public boolean hasAnyPermission(List<String> permissions){
        // 获取当前登录用户的权限列表
        List<String> userPermissions = SecurityUtil.getLoginUserVo().getPermissions();
        // 判断是否具有任一权限
        return userPermissions.stream().anyMatch(permissions::contains);
    }

}
