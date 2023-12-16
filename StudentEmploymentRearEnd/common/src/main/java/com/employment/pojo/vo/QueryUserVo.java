package com.employment.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryUserVo
 * @author: c9noo
 * @create: 2023-11-15 19:14
 * @Version 1.0
 * 用户列表返回视图
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserVo implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 名称
     */
    private String name;
    /**
     * 登录账号
     */
    private String username;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 登录密码
     */
    private String password;

    private static final long serialVersionUID = -8529025738763411213L;
}
