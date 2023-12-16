package com.employment.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: StudentEmploymentSystem
 * @ClassName UserVo
 * @author: c9noo
 * @create: 2023-11-09 09:02
 * @Version 1.0
 * 用户信息返回
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户登录账号
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 联系号码
     */
    private String phone;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;
}
