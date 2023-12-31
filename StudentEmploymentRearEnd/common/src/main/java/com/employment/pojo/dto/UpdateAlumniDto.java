package com.employment.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @program: StudentEmploymentSystem
 * @ClassName UpdateAlumniDto
 * @author: c9noo
 * @create: 2023-12-17 10:15
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAlumniDto {
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

}
