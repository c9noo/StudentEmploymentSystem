package com.employment.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.employment.utils.LocalDateStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ExportAlumniVo
 * @author: c9noo
 * @create: 2023-12-15 12:12
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportAlumniVo {

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String name;

    /**
     * 当前职位
     */
    @ExcelProperty("当前职位")
    private String currentPosition;

    /**
     * 在职公司
     */
    @ExcelProperty("在职公司")
    private String currentCompany;

    /**
     * 地点
     */
    @ExcelProperty("地点")
    private String location;

    /**
     * 薪水
     */
    @ExcelProperty("薪水")
    private BigDecimal salary;

    /**
     * 联系方式
     */
    @ExcelProperty("联系方式")
    private String phone;

    /**
     * 毕业时间
     */
    @ExcelProperty(value = "毕业时间",converter = LocalDateStringConverter.class)
    private LocalDate graduationDate;

    /**
     * 班主任
     */
    @ExcelProperty("班主任")
    private String classAdvisor;

    /**
     * 最后更新时间
     */
    @ExcelProperty(value = "最后更新时间",converter = LocalDateStringConverter.class)
    private LocalDate  lastUpdateTime;

}
