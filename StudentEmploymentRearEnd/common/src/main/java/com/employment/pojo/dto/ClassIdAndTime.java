package com.employment.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ClassIdAndTime
 * @author: c9noo
 * @create: 2023-12-29 16:29
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassIdAndTime {
    /**
     * id
     */
    private Long id;

    /**
     * 毕业时间
     */
    private LocalDate graduationDate;

    /**
     * 状态（0为未毕业，1为毕业班，2为已毕业）
     */
    private Integer status;
}
