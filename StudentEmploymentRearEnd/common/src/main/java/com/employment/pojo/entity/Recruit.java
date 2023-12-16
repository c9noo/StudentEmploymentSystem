package com.employment.pojo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

/**
 * 招聘信息表
 * @TableName tb_recruit
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Recruit implements Serializable {
    /**
     * 招聘信息id
     */
    private Long id;

    /**
     * 职位名称
     */
    private String jobName;

    /**
     * 工作地点
     */
    private String jobAddress;

    /**
     * 要求学历
     */
    private String education;

    /**
     * 最少工资
     */
    private Integer minSalary;

    /**
     * 最大工资
     */
    private Integer maxSalary;

    /**
     * 工作经验
     */
    private String jobExperience;

    /**
     * 状态(0正在招聘)
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 是否删除(0未删除)
     */
    private Integer isDelete;

    /**
     * 最少需要人
     */
    private Integer needPeople;

    /**
     * 是否置顶（1为置顶）
     */
    private Boolean isPinned;

    private static final long serialVersionUID = 1L;
}