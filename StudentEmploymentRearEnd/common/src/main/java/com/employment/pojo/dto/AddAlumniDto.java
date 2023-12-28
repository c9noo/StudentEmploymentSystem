package com.employment.pojo.dto;

import com.employment.constant.ParamErrorConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AddAlumniDto
 * @author: c9noo
 * @create: 2023-12-15 14:17
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAlumniDto {

    /**
     * 姓名
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String name;

    /**
     * 当前职位
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String currentPosition;

    /**
     * 在职公司
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String currentCompany;

    /**
     * 地点
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String location;

    /**
     * 薪水
     */
    private BigDecimal salary;

    /**
     * 联系方式
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String phone;

    /**
     * 毕业时间
     */
    private LocalDate graduationDate;

    /**
     * 班主任
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String classAdvisor;

}
