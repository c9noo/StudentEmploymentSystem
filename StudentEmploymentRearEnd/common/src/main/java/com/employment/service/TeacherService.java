package com.employment.service;

import com.employment.pojo.dto.AddUserDto;
import com.employment.pojo.dto.ImportUserDto;
import com.employment.pojo.dto.QueryTeacherDto;
import com.employment.pojo.dto.UpdateUserDto;
import com.employment.pojo.vo.ExportUserVo;
import com.employment.pojo.vo.QueryTeacherVo;
import com.employment.result.PageResult;
import com.employment.result.ResponseResult;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName TeacherService
 * @author: c9noo
 * @create: 2023-12-12 09:49
 * @Version 1.0
 **/
public interface TeacherService {

    /**
     * 分页查询老师列表
     * @param queryTeacherDto
     * @return
     */
    PageResult pageQuery(QueryTeacherDto queryTeacherDto);

    /**
     * 批量插入老师
     * @param importUserDtoList
     * @return
     */
    ResponseResult importTeacher(List<ImportUserDto> importUserDtoList);

    /**
     * 查询老师信息
     * @return
     */
    List<ExportUserVo> getAllTeacher();

    /**
     * 增加老师
     * @param addUserDto
     * @return
     */
    ResponseResult addTeacher(AddUserDto addUserDto);

    /**
     * 修改老师信息
     * @param updateUserDto
     * @return
     */
    ResponseResult updateTeacher(UpdateUserDto updateUserDto);

    /**
     * 删除老师信息
     * @param ids
     * @return
     */
    ResponseResult removeByIds(List<String> ids);

    /**
     * 修改老师状态
     * @param status
     * @param id
     * @return
     */
    ResponseResult stopAndStart(Integer status, Long id);

    /**
     * 查询老师详情
     * @param id
     * @return
     */
    QueryTeacherVo getTeacherById(Long id);
}
