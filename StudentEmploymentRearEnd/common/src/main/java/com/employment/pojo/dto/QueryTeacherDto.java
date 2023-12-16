package com.employment.pojo.dto;

import com.employment.constant.FieldErrorConstant;
import com.employment.constant.PageConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryTeacherDto
 * @author: c9noo
 * @create: 2023-12-13 08:26
 * @Version 1.0
 * 分页查询老师列表条件
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryTeacherDto {
    @NotNull(message = FieldErrorConstant.RECRUIT_STATUS_ERROR)
    private Integer page;
    @NotNull(message = FieldErrorConstant.RECRUIT_STATUS_ERROR)
    private Integer pageSize;
    private String name;
    @NotNull
    @Max(value = PageConstant.MAX,message = FieldErrorConstant.RECRUIT_STATUS_ERROR) //设置参数的最大值
    @Min(value = PageConstant.MIN,message = FieldErrorConstant.RECRUIT_STATUS_ERROR)  //设置参数的最小值
    @Digits(integer = PageConstant.INTEGER_NUM,fraction = PageConstant.FRACTION_NUM,message = FieldErrorConstant.RECRUIT_STATUS_ERROR)  //设置整数部分为1位，小数部分0位
    private Integer status;
}
