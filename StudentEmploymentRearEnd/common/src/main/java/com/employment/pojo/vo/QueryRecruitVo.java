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
 * @ClassName QueryRecruitVo
 * @author: c9noo
 * @create: 2023-11-14 15:11
 * @Version 1.0
 * 分页查询返回的内容
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRecruitVo implements Serializable {
    /**
     * 招聘信息id
     */
    private Long id;

    /**
     * 招聘人名称
     */
    private String name;

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
     * 需要人数
     */
    private Integer needPeople;

    /**
     * 状态(0正在招聘)
     */
    private Integer status;

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否置顶（1为置顶）
     */
    private Boolean isPinned;

    private static final long serialVersionUID = -8529025738763411213L;
}
