package com.employment.pojo.dto;

import com.employment.constant.FieldErrorConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @program: StudentEmploymentSystem
 * @ClassName QueryAlumniDto
 * @author: c9noo
 * @create: 2023-12-14 15:07
 * @Version 1.0
 * 分页查询校友列表
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryAlumniDto {
    @NotNull(message = FieldErrorConstant.RECRUIT_STATUS_ERROR)
    private Integer page;
    @NotNull(message = FieldErrorConstant.RECRUIT_STATUS_ERROR)
    private Integer pageSize;
    private String name;
}
