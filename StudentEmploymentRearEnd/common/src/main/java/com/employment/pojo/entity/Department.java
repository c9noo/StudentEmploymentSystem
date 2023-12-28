package com.employment.pojo.entity;

import java.io.Serializable;

import lombok.*;

/**
 * 
 * @TableName tb_department
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Department implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 系部名
     */
    private String name;

    /**
     * 管理人
     */
    private String manager;

    /**
     * 管理人电话
     */
    private String managerPhone;

    /**
     * 是否删除(0未删除)
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}