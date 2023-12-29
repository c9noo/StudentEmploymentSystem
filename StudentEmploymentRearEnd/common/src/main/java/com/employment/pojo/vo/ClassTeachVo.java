package com.employment.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ClassTeachVo
 * @author: c9noo
 * @create: 2023-12-29 15:44
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassTeachVo {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;
}
