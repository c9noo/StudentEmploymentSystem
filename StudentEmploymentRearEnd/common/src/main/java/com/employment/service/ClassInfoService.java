package com.employment.service;

import com.employment.pojo.dto.AddClassDto;
import com.employment.pojo.dto.QueryClassInfoDto;
import com.employment.pojo.vo.QueryClassDetailVo;
import com.employment.result.PageResult;
import com.employment.result.ResponseResult;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ClassInfoService
 * @author: c9noo
 * @create: 2023-12-18 09:28
 * @Version 1.0
 **/
public interface ClassInfoService {

    /**
     * 分页查询班级信息
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

    /**
     * 删除班级
     * @param id
     * @return
     */
    ResponseResult removeById(Long id);

    /**
     * 增加班级
     * @param addClassDto
     * @return
     */
    ResponseResult addClassInfo(AddClassDto addClassDto);

    /**
     * 获取老师id和姓名
     * @return
     */
    ResponseResult getTeacherInfoAndId();
}
