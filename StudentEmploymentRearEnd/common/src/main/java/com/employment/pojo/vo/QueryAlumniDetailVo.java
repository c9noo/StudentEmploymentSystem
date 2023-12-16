package com.employment.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryAlumniDetailVo
 * @author: c9noo
 * @create: 2023-12-15 14:37
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryAlumniDetailVo {
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
}
