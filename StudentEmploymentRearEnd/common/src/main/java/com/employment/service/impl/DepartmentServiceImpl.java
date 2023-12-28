package com.employment.service.impl;

import com.employment.mapper.DepartmentMapper;
import com.employment.pojo.vo.DepartmentTagVo;
import com.employment.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName DepartmentServiceImpl
 * @author: c9noo
 * @create: 2023-12-28 10:51
 * @Version 1.0
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentTagVo> getDepartment() {
        return departmentMapper.getDepartment();
    }
}
