package com.employment.pojo.dto;

import com.employment.constant.ParamErrorConstant;
import com.employment.constant.ParamExpreConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @program: StudentEmploymentSystem
 * @ClassName UpdateUserDto
 * @author: c9noo
 * @create: 2023-11-13 09:35
 * @Version 1.0
 * 接收用户修改时发送的数据
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    /**
     * 用户的id
     */
    private Long id;

    /**
     * 用户名称
     */
    @NotBlank(message = ParamErrorConstant.NAME_ERROR)
    private String name;


    /**
     * 头像
     */
    @NotNull(message = ParamErrorConstant.PARAM_IS_NULL)
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
