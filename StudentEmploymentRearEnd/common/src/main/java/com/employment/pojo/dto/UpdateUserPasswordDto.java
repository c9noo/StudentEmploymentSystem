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
 * @ClassName UpdateUserPasswordDto
 * @author: c9noo
 * @create: 2023-12-13 10:26
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPasswordDto {
    /**
     * 旧密码
     */
    @NotBlank(message = ParamErrorConstant.PASSWORD_ERROR)
    @Pattern(regexp = ParamExpreConstant.PASSWORD,message = ParamErrorConstant.PASSWORD_ERROR)
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = ParamErrorConstant.PASSWORD_ERROR)
    @Pattern(regexp = ParamExpreConstant.PASSWORD,message = ParamErrorConstant.PASSWORD_ERROR)
    private String newPassword;
}
