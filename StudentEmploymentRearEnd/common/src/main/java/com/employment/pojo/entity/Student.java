package com.employment.pojo.entity;

import java.io.Serializable;

import lombok.*;

/**
 * 
 * @TableName tb_student
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Student implements Serializable {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 微信唯一标识
     */
    private String openId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态(0为在读，1为毕业，2为退学，-1为匿名用户，默认值是-1)
     */
    private Integer status;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 专业
     */
    private String speciality;

    private static final long serialVersionUID = 1L;
}