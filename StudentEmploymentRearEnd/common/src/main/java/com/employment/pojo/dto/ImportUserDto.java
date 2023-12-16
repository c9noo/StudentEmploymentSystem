package com.employment.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.employment.constant.ParamErrorConstant;
import com.employment.constant.ParamExpreConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ImportUserDto
 * @author: c9noo
 * @create: 2023-11-27 09:17
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportUserDto {
    /**
     * 用户名称
     */
    @ExcelProperty(index = 0)
    @NotBlank(message = ParamErrorConstant.NAME_ERROR)
    private String name;

    /**
     * 用户登录账号
     */
    @ExcelProperty(index = 1)
    @NotBlank(message = ParamErrorConstant.USERNAME_ERROR)
    private String username;

    /**
     * 用户密码
     */
    @ExcelProperty(index = 2)
    @NotBlank(message = ParamErrorConstant.PASSWORD_ERROR)
    @Pattern(regexp = ParamExpreConstant.PASSWORD,message = ParamErrorConstant.PASSWORD_ERROR)
    private String password;

    /**
     * 联系号码
     */
    @ExcelProperty(index = 3)
    @NotBlank(message = ParamErrorConstant.PHONE_ERROR)
    @Pattern(regexp = ParamExpreConstant.PHONE, message = ParamErrorConstant.PHONE_ERROR)
    private String phone;

    /**
     * 邮箱地址
     */
    @ExcelProperty(index = 4)
    @Email(message = ParamErrorConstant.EMAIL_ERROR)
    private String email;
}
