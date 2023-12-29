package com.employment.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryClassDetailVo
 * @author: c9noo
 * @create: 2023-12-18 14:33
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryClassDetailVo {
    /**
     * 班级id
     */
    private Long id;

    /**
     * 班主任id
     */
    private Long userId;

    /**
     * 班级名
     */
    private String name;

    /**
     * 班主任姓名
     */
    private String adviser;

    /**
     * 状态（0为未毕业，1为毕业班，2为已毕业）
     */
    private Integer status;

    /**
     * 毕业时间
     */
    private Date graduationDate;

    /**
     * 所属系部名称
     */
    private String departmentName;

    /**
     * 班级的学生
     */
    private List<ClassStudentVo> classStudentVoList;
}
