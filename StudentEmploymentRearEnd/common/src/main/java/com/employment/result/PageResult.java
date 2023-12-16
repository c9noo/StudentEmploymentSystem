package com.employment.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName PageResult
 * @author: c9noo
 * @create: 2023-11-14 15:20
 * @Version 1.0
 * 分页查询的返回处理
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult implements Serializable {
    private Integer total; //总记录数

    private List records; //当前页数据集合
}
