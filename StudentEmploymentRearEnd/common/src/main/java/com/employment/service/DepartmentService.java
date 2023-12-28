package com.employment.service;

import com.employment.pojo.vo.DepartmentTagVo;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName DepartmentService
 * @author: c9noo
 * @create: 2023-12-28 10:51
 * @Version 1.0
 **/
public interface DepartmentService {

    /**
     * 查询部门标签
     * @return
     */
    List<DepartmentTagVo> getDepartment();
}
