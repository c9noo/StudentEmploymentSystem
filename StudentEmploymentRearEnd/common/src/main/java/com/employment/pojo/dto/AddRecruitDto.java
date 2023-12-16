package com.employment.pojo.dto;

import com.employment.constant.ParamErrorConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AddRecruitDto
 * @author: c9noo
 * @create: 2023-12-07 08:25
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRecruitDto {

    /**
     * 职位名称
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String jobName;

    /**
     * 工作地点
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String jobAddress;

    /**
     * 要求学历
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String education;

    /**
     * 最少工资
     */
    @Digits(integer = 10, fraction = 0, message = ParamErrorConstant.PARAM_ERROR)
    @NotNull(message = ParamErrorConstant.PARAM_IS_NULL)
    private Integer minSalary;

    /**
     * 最大工资
     */
    @Digits(integer = 10, fraction = 0, message = ParamErrorConstant.PARAM_ERROR)
    @NotNull(message = ParamErrorConstant.PARAM_IS_NULL)
    private Integer maxSalary;

    /**
     * 工作经验
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String jobExperience;

    /**
     * 内容
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String content;

    /**
     * 最少需要人
     */
    @NotNull(message = ParamErrorConstant.PARAM_IS_NULL)
    @Digits(integer = 10, fraction = 0, message = ParamErrorConstant.PARAM_ERROR)
    private Integer needPeople;

}
