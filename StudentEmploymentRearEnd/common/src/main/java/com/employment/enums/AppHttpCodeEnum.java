package com.employment.enums;

import com.employment.constant.FileSuffixConstant;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AppHttpCodeEnum
 * @author: c9noo
 * @create: 2023-11-06 18:45
 * @Version 1.0
 * 自定义枚举类，进行限制错误提示的信息和响应码
 **/
public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功"),
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作，请联系管理员添加权限"),
    SYSTEM_ERROR(500,"出现错误"),
    DATA_ERROR(501,"数据出错"),
    FILE_UPLOAD(502,"文件上传失败"),
    FILE_EXTENSION_NOT_ALLOWED(503,"只支持"+ FileSuffixConstant.FILE_SUFFiX),
    USER_STATUS_ERROR(504, "被锁定"),

    NULL_ERROR(505,"信息未找到"),
    PARAM_ERROR(506,"参数错误"),

    LOGIN_ERROR(507,"用户名或密码错误"),

    CREATE_USER_ERROR(508, "用户名或登录名已存在"),
    DATA_IS_NULL(509,"数据不存在"),

    POWER_ERROR(510,"权限不够"),

    CAPTCHA_ERROR(511, "验证码错误"),

    OLD_PASSWORD_ERROR(512,"旧密码错误"),
    EMAIL_EXIST(513,"邮箱不存在" ),
    EMAIL_TIME_NOT_OUT(514,"请在三分钟之后在重新获取" ),
    PINNED_ERROR(515,"置顶招聘只允许五个" ),

    EMAIL_PHONE_ERROR(516, "邮箱号或是手机号重复")

    ;


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
