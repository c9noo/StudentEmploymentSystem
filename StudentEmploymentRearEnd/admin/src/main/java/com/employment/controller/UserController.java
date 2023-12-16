package com.employment.controller;

import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.pojo.dto.LoginUserDto;
import com.employment.pojo.dto.UpdateUserDto;
import com.employment.pojo.dto.UpdateUserPasswordDto;
import com.employment.result.ResponseResult;
import com.employment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * @program: StudentEmploymentSystem
 * @ClassName UserController
 * @author: c9noo
 * @create: 2023-11-06 14:38
 * @Version 1.0
 * 用户操作相关controller
 **/
@RestController
@RequestMapping("/admin/user")
@Api(tags = "用户相关接口")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;



    /**
     * 用户登录接口
     * @param loginUserDto
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ResponseResult login(@RequestBody @Validated LoginUserDto loginUserDto){
        return userService.login(loginUserDto);
    }

    /**
     * 获取用户信息 和 权限信息
     * @return
     */
    @GetMapping("/getInfo")
    @ApiOperation("用户获取信息,权限")
    public ResponseResult getInfo(){
        return ResponseResult.okResult(userService.getInfo());
    }

    /**
     * 查询用户对应的菜单信息
     * @return
     */
    @GetMapping("/getMenus")
    @ApiOperation("用户获取菜单")
    public ResponseResult getMenus(){
        return ResponseResult.okResult(userService.getMenus());
    }

    /**
     * 用户退出功能
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation("用户退出功能")
    public ResponseResult logout(){
      return userService.logout();
    }

    /**
     * 根据Token修改用户功能接口
     * @param updateUserDto
     * @return
     */
    @PutMapping
    @ApiOperation("用户修改功能")
    public ResponseResult updateUserByToken(@RequestBody @Validated UpdateUserDto updateUserDto){
        return userService.updateUserByToken(updateUserDto);
    }

    /**
     * 修改密码
     * @param updateUserPassword
     * @return
     */
    @PutMapping("/password")
    @ApiOperation("修改密码")
    public ResponseResult password(@RequestBody @Validated UpdateUserPasswordDto updateUserPassword){
        return userService.updateUserPassword(updateUserPassword);
    }

    /**
     * 获取验证码
     * @return
     * @throws IOException
     */
    @GetMapping("/captcha")
    @ApiOperation("获取验证码")
    public ResponseResult getCaptcha() throws IOException {
        return userService.getCaptcha();
    }

}
