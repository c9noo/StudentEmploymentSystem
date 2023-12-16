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
 * @ClassName UpdateRecruitDto
 * @author: c9noo
 * @create: 2023-12-07 10:58
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecruitDto {
    /**
     * 招聘信息id
     */
    @NotNull(message = ParamErrorConstant.PARAM_IS_NULL)
    private Long id;

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
    private Integer needPeople;

    /**
     * 状态(0正在招聘)
     */
    @NotNull(message = ParamErrorConstant.PARAM_IS_NULL)
    private Integer status;
}
