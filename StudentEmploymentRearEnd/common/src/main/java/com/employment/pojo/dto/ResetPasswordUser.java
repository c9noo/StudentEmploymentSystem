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
 * @ClassName ResetPasswordUser
 * @author: c9noo
 * @create: 2023-12-13 16:32
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordUser {

    @Email(message = ParamErrorConstant.EMAIL_ERROR)
    private String email;

    @NotBlank(message = ParamErrorConstant.PASSWORD_ERROR)
    @Pattern(regexp = ParamExpreConstant.PASSWORD,message = ParamErrorConstant.PASSWORD_ERROR)
    private String password;

    @NotBlank(message = ParamErrorConstant.CAPTCHA_NOT_NULL)
    private String text;
}
