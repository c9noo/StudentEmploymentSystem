package com.employment.pojo.dto;

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
 * @ClassName AddUserDto
 * @author: c9noo
 * @create: 2023-11-26 19:53
 * @Version 1.0
 * 新增用户
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDto {
    /**
     * 用户名称
     */
    @NotBlank(message = ParamErrorConstant.NAME_ERROR)
    private String name;

    /**
     * 用户登录账号
     */
    @NotBlank(message = ParamErrorConstant.USERNAME_ERROR)
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = ParamErrorConstant.PASSWORD_ERROR)
    @Pattern(regexp = ParamExpreConstant.PASSWORD,message = ParamErrorConstant.PASSWORD_ERROR)
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 联系号码
     */
    @NotBlank(message = ParamErrorConstant.PHONE_ERROR)
    @Pattern(regexp = ParamExpreConstant.PHONE, message = ParamErrorConstant.PHONE_ERROR)
    private String phone;

    /**
     * 邮箱地址
     */
    @Email(message = ParamErrorConstant.EMAIL_ERROR)
    private String email;
}
