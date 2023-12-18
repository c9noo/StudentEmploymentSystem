package com.employment.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ClassStudentVo
 * @author: c9noo
 * @create: 2023-12-18 14:40
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassStudentVo {
    /**
     * 用户id
     */
    private Integer id;

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
     * 状态(0为在读，1为毕业，-1为匿名用户，默认值是-1)
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
}
