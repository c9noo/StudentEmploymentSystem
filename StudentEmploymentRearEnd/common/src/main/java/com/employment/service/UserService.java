package com.employment.service;

import com.employment.pojo.dto.LoginUserDto;
import com.employment.pojo.dto.ResetPasswordUser;
import com.employment.pojo.dto.UpdateUserDto;
import com.employment.pojo.dto.UpdateUserPasswordDto;
import com.employment.pojo.vo.MenusVo;
import com.employment.pojo.vo.UserInfoVo;
import com.employment.result.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @program: StudentEmploymentSystem
 * @ClassName UserService
 * @author: c9noo
 * @create: 2023-11-06 14:41
 * @Version 1.0
 **/
public interface UserService {

    ResponseResult login(LoginUserDto loginUserDto);

    UserInfoVo getInfo();

    MenusVo getMenus();

    ResponseResult logout();

    ResponseResult updateUserByToken(UpdateUserDto updateUserDto);

    ResponseResult getCaptcha() throws IOException;

    /**
     * 修改密码
     * @param updateUserPassword
     * @return
     */
    ResponseResult updateUserPassword(UpdateUserPasswordDto updateUserPassword);

    /**
     * 发送邮件
     * @param email
     * @param request
     * @return
     */
    ResponseResult sendValidationEmail(String email, HttpServletRequest request);

    ResponseResult reset(ResetPasswordUser resetPasswordUser);
}
