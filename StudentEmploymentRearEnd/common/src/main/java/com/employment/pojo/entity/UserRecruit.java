package com.employment.pojo.entity;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

/**
 * 用户与招聘信息关联表
 * @TableName tb_user_recruit
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRecruit implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 招聘id
     */
    private Long recruitId;

    private static final long serialVersionUID = 1L;
}