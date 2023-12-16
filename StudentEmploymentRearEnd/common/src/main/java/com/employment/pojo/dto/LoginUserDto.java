package com.employment.pojo.dto;

import com.employment.constant.ParamErrorConstant;
import com.employment.constant.ParamExpreConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @program: StudentEmploymentSystem
 * @ClassName LoginUserDto
 * @author: c9noo
 * @create: 2023-11-06 19:33
 * @Version 1.0
 * 接收用户登录时发送的数据
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    @NotBlank(message = ParamErrorConstant.USERNAME_ERROR)
    private String username;
    @NotBlank(message = ParamErrorConstant.PASSWORD_ERROR)
    @Pattern(regexp = ParamExpreConstant.PASSWORD,message = ParamErrorConstant.PASSWORD_ERROR)
    private String password;
    @NotBlank(message = ParamErrorConstant.CAPTCHA_NOT_NULL)
    private String captcha;
    @NotBlank(message = ParamErrorConstant.CAPTCHA_NOT_NULL)
    private String uuid;
}
