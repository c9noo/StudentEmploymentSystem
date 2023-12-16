package com.employment.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryCompanyVo
 * @author: c9noo
 * @create: 2023-11-26 11:38
 * @Version 1.0
 * 查询企业信息详情返回视图
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class QueryCompanyVo implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户登录账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 联系号码
     */
    private String phone;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 状态(0未禁用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除（0未删除）
     */
    private Integer isDelete;

    /**
     * 更新人的名字
     */
    private String updateUsername;

    private static final long serialVersionUID = -8529025738763411213L;

}
