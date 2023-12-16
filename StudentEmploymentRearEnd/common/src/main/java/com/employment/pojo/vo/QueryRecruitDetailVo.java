package com.employment.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryRecruitDetailVo
 * @author: c9noo
 * @create: 2023-11-15 09:33
 * @Version 1.0
 * 根据id查询招聘详情返回的Vo
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryRecruitDetailVo implements Serializable {
    /**
     * 招聘信息id
     */
    private Long id;

    /**
     * 公司名字
     */
    private String companyName;

    /**
     * 公司头像
     */
    private String companyAvatar;

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
     * 内容
     */
    private String content;

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
     * 招聘状态
     */
    private Integer status;

    /**
     * 需要人数
     */
    private Integer needPeople;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;

    /**
     * 浏览人数
     */
    private Integer viewCount;

    /**
     * 是否置顶（1为置顶）
     */
    private Boolean isPinned;

    private static final long serialVersionUID = -8529025738763411213L;
}
