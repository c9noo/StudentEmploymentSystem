package com.employment.service;

import com.employment.pojo.dto.AddUserDto;
import com.employment.pojo.dto.ImportUserDto;
import com.employment.pojo.dto.QueryCompanyDto;
import com.employment.pojo.dto.UpdateUserDto;
import com.employment.pojo.vo.ExportUserVo;
import com.employment.pojo.vo.QueryCompanyVo;
import com.employment.result.PageResult;
import com.employment.result.ResponseResult;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName CompanyService
 * @author: c9noo
 * @create: 2023-11-15 18:56
 * @Version 1.0
 * 企业操作相关
 **/
public interface CompanyService {


    /**
     * 分页查询企业列表
     * @param queryCompanyDto
     * @return
     */
    PageResult pageQuery(QueryCompanyDto queryCompanyDto);

    /**
     * 修改企业状态
     * @param status 要修改的状态
     * @param id 要修改的id
     * @return
     */
    ResponseResult stopAndStart(Integer status, Long id);

    /**
     * 查询企业详情
     * @param id
     * @return
     */
    QueryCompanyVo getCompanyById(Long id);

    /**
     * 根据企业id删除企业
     * @param ids
     * @return
     */
    ResponseResult removeByIds(List<String> ids);

    /**
     * 根据企业的id修改企业
     * @param updateUserDto
     * @return
     */
    ResponseResult updateCompany(UpdateUserDto updateUserDto);

    /**
     * 新增企业
     * @param addUserDto
     * @return
     */
    ResponseResult addCompany(AddUserDto addUserDto);

    /**
     * 批量导入企业
     * @param companyList
     * @return
     */
    ResponseResult importCompany(List<ImportUserDto> companyList);

    /**
     * 查询所用的企业信息
     * @return
     */
    List<ExportUserVo> getAllCompany();
}
