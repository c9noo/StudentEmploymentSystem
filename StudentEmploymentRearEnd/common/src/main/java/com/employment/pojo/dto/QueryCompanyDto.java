package com.employment.pojo.dto;

import com.employment.constant.FieldErrorConstant;
import com.employment.constant.PageConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryCompanyDto
 * @author: c9noo
 * @create: 2023-11-20 15:08
 * @Version 1.0
 * 分页查询企业列表Dto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCompanyDto {
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
