package com.employment.pojo.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_class_deparrment
 */
@Data
public class ClassDeparrment implements Serializable {
    /**
     * 班级id
     */
    private Long classId;

    /**
     * 系部id
     */
    private Long departmentId;

    private static final long serialVersionUID = 1L;
}