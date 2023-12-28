package com.employment.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: StudentEmploymentSystem
 * @ClassName DepartmentTagVo
 * @author: c9noo
 * @create: 2023-12-28 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentTagVo {
    /**
     * id
     */
    private Long id;

    /**
     * 系部名
     */
    private String name;
}
