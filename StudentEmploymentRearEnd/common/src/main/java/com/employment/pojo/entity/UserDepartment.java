package com.employment.pojo.entity;

import java.io.Serializable;

import lombok.*;

/**
 * 
 * @TableName tb_user_department
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDepartment implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 系部id
     */
    private Long departmentId;

    private static final long serialVersionUID = 1L;
}