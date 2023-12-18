package com.employment.pojo.dto;

import com.employment.constant.FieldErrorConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryClassInfoDto
 * @author: c9noo
 * @create: 2023-12-18 09:37
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryClassInfoDto {


    @NotNull(message = FieldErrorConstant.RECRUIT_STATUS_ERROR)
    private Integer page;

    @NotNull(message = FieldErrorConstant.RECRUIT_STATUS_ERROR)
    private Integer pageSize;

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

}
