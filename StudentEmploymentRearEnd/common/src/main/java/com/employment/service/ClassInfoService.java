package com.employment.service;

import com.employment.pojo.dto.QueryClassInfoDto;
import com.employment.pojo.vo.QueryClassDetailVo;
import com.employment.result.PageResult;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ClassInfoService
 * @author: c9noo
 * @create: 2023-12-18 09:28
 * @Version 1.0
 **/
public interface ClassInfoService {

    /**
     * 分页查询学生信息
     * @param queryClassInfoDto
     * @return
     */
    PageResult pageQuery(QueryClassInfoDto queryClassInfoDto);

    /**
     * 获取班级详情
     * @param id
     * @return
     */
    QueryClassDetailVo getClassById(Long id);
}
