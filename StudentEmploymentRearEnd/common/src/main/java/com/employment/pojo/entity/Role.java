package com.employment.pojo.entity;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

/**
 * 角色信息表
 * @TableName tb_role
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Role implements Serializable {
    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色状态（0正常）
     */
    private Integer status;

    /**
     * 删除标志（0代表存在）
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}