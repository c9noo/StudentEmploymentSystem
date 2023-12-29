package com.employment.pojo.dto;

import com.employment.constant.ParamErrorConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AddClassDto
 * @author: c9noo
 * @create: 2023-12-18 15:43
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddClassDto {
    /**
     * 班级名
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String name;

    /**
     * 老师id
     */
    private Long userId;

    /**
     * 班主任姓名
     */
    @NotBlank(message = ParamErrorConstant.PARAM_IS_NULL)
    private String adviser;

    /**
     * 状态（0为未毕业，1为毕业班，2为已毕业）
     */
    private Integer status;

    /**
     * 毕业时间
     */
    private LocalDate graduationDate;

    /**
     * 所属系部id
     */
    private Long departmentId;
}
