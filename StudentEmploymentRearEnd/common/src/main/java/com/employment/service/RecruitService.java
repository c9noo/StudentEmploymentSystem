package com.employment.service;

import com.employment.pojo.dto.AddRecruitDto;
import com.employment.pojo.dto.QueryRecruitDto;
import com.employment.pojo.dto.UpdateRecruitDto;
import com.employment.pojo.vo.QueryRecruitDetailVo;
import com.employment.result.PageResult;
import com.employment.result.ResponseResult;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName RecruitService
 * @author: c9noo
 * @create: 2023-11-14 14:47
 * @Version 1.0
 * 招聘信息service的接口定义
 **/
public interface RecruitService {

    /**
     * 查询招聘信息详情
     * @param id
     * @return
     */
    QueryRecruitDetailVo getRecruitDetailById(Long id);

    /**
     * 分页查询招聘信息
     * @param queryRecruitDto
     * @return
     */
    PageResult pageQuery(QueryRecruitDto queryRecruitDto,Long id);

    /**
     * 修改招聘信息的状态
     * @param status
     * @param id
     * @return
     */
    ResponseResult stopAndStart(Integer status, Long id);

    /**
     * 根据id删除招聘信息
     * @param ids
     * @return
     */
    ResponseResult removeByIds(List<String> ids);

    /**
     * 新增招聘
     * @param addRecruitDto
     * @return
     */
    ResponseResult addRecruit(AddRecruitDto addRecruitDto);

    /**
     * 更新招聘
     * @param updateRecruitDto
     * @return
     */
    ResponseResult updateRecruit(UpdateRecruitDto updateRecruitDto);


    /**
     * 是否置顶
     * @param id
     * @param pinned
     * @return
     */
    ResponseResult updatePinned(Long id, boolean pinned);
}
