package com.employment.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName UserInfoVo
 * @author: c9noo
 * @create: 2023-11-09 08:58
 * @Version 1.0
 * 封装返回用户信息 和 权限信息 还有角色信息
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    /**
     * 用户信息
     */
    private UserVo user;

    /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 权限信息
     */
    private List<String> permissions;
}
