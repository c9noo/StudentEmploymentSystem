package com.employment.pojo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

/**
 * 
 * @TableName tb_alumni
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Alumni implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 当前职位
     */
    private String currentPosition;

    /**
     * 在职公司
     */
    private String currentCompany;

    /**
     * 地点
     */
    private String location;

    /**
     * 薪水
     */
    private BigDecimal salary;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 毕业时间
     */
    private LocalDate graduationDate;

    /**
     * 班主任
     */
    private String classAdvisor;

    /**
     * 最后更新时间
     */
    private LocalDate  lastUpdateTime;

    /**
     * 逻辑删除（0未删除）
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}