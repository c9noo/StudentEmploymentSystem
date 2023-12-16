package com.employment.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ExportUserVo
 * @author: c9noo
 * @create: 2023-11-27 09:09
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportUserVo {

    /**
     * 用户名称
     */
    @ExcelProperty("用户名称")
    private String name;

    /**
     * 用户登录账号
     */
    @ExcelProperty("用户登录账号")
    private String username;

    /**
     * 联系号码
     */
    @ExcelProperty("联系号码")
    private String phone;

    /**
     * 邮箱地址
     */
    @ExcelProperty("邮箱地址")
    private String email;

    /**
     * 状态(0未禁用)
     */
    @ExcelProperty("状态(0未禁用)")
    private Integer status;

}
