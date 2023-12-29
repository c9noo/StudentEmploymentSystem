package com.employment.pojo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import lombok.*;

/**
 * 
 * @TableName tb_classInformation
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ClassInformation implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 班级名
     */
    private String name;

    /**
     * 班主任id
     */
    private Long userId;

    /**
     * 班主任姓名
     */
    private String adviser;

    /**
     * 状态（0为未毕业，1为毕业班，2为已毕业）
     */
    private Integer status;

    /**
     * 毕业时间
     */
    private LocalDate graduationDate;

    /**
     * 逻辑删除（0未删除）
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}