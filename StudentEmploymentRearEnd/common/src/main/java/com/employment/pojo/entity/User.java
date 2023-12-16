package com.employment.pojo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

/**
 * 
 * @TableName tb_user
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements Serializable {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户登录账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 头像
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
     * 状态(0未禁用)
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除（0未删除）
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}