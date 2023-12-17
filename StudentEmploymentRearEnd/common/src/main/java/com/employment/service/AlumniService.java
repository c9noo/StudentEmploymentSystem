package com.employment.service;

import com.employment.pojo.dto.AddAlumniDto;
import com.employment.pojo.dto.ImportAlumniDto;
import com.employment.pojo.dto.QueryAlumniDto;
import com.employment.pojo.dto.UpdateAlumniDto;
import com.employment.pojo.vo.ExportAlumniVo;
import com.employment.pojo.vo.ExportUserVo;
import com.employment.pojo.vo.QueryAlumniDetailVo;
import com.employment.result.PageResult;
import com.employment.result.ResponseResult;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AlumniService
 * @author: c9noo
 * @create: 2023-12-14 14:55
 * @Version 1.0
 **/
public interface AlumniService {

    /**
     * 分页查询
     * @param queryAlumniDto
     * @return
     */
    PageResult pageQuery(QueryAlumniDto queryAlumniDto);

    ResponseResult importTeacher(List<ImportAlumniDto> importAlumniDtoList);

    /**
     * 导出校友信息
     * @return
     */
    List<ExportAlumniVo> getAllAlumni();

    /**
     * 增加校友
     * @param addAlumniDto
     * @return
     */
    ResponseResult addAlumni(AddAlumniDto addAlumniDto);

    /**
     * 获取校友详情
     * @param id
     * @return
     */
    QueryAlumniDetailVo getAlumniById(Long id);

    ResponseResult updateAlumni(UpdateAlumniDto updateAlumniDto);

    ResponseResult removeAlumniById(List<Long> ids);
}
