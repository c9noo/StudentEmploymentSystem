package com.employment.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.employment.constant.ParamErrorConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ImportAlumniDto
 * @author: c9noo
 * @create: 2023-12-14 16:36
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportAlumniDto {

    /**
     * 姓名
     */
    @ExcelProperty(index = 0)
    @NotBlank(message = ParamErrorConstant.NAME_ERROR)
    private String name;

    /**
     * 当前职位
     */
    @ExcelProperty(index = 1)
    @NotBlank(message = ParamErrorConstant.CURRENT_POSITION_ERROR)
    private String currentPosition;

    /**
     * 在职公司
     */
    @ExcelProperty(index = 2)
    @NotBlank(message = ParamErrorConstant.CURRENT_COMPANY_ERROR)
    private String currentCompany;

    /**
     * 地点
     */
    @ExcelProperty(index = 3)
    @NotBlank(message = ParamErrorConstant.LOCATION_ERROR)
    private String location;

    /**
     * 薪水
     */
    @ExcelProperty(index = 4)
    @NotNull(message = ParamErrorConstant.SALARY_ERROR)
    private BigDecimal salary;

    /**
     * 联系方式
     */
    @ExcelProperty(index = 5)
    @NotBlank(message = ParamErrorConstant.PHONE_ERROR)
    private String phone;

    /**
     * 毕业时间
     */
    @ExcelProperty(index = 6)
//    @Past(message = ParamErrorConstant.GRADUATION_DATA_ERROR)
//    @DateTimeFormat(pattern = "yyyy/M/d")
    private String graduationDate;

    /**
     * 班主任
     */
    @ExcelProperty(index = 7)
    @NotBlank(message = ParamErrorConstant.CLASS_ADVISOR_ERROR)
    private String classAdvisor;


}
