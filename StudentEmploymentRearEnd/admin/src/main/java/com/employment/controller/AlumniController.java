package com.employment.controller;

import com.alibaba.excel.EasyExcel;
import com.employment.constant.AuthorityConstant;
import com.employment.constant.RedisConstant;
import com.employment.pojo.dto.*;
import com.employment.pojo.vo.ExportAlumniVo;
import com.employment.pojo.vo.ExportUserVo;
import com.employment.result.ResponseResult;
import com.employment.service.AlumniService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AlumniController
 * @author: c9noo
 * @create: 2023-12-14 14:50
 * @Version 1.0
 * 校友控制层
 **/
@RestController
@RequestMapping("/admin/alumni")
@Api("校友管理")
@Validated
public class AlumniController {

    @Autowired
    private AlumniService alumniService;

    /**
     * 分页查询校友列表
     * @param queryAlumniDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+ AuthorityConstant.ALUMNI_LIST+"')")
    @GetMapping("/page")
    @ApiOperation("校友信息分页查询")
    @Cacheable(cacheNames = RedisConstant.REDIS_ALUMNI_PAGE,
            key = "#queryAlumniDto.page + ':' + #queryAlumniDto.pageSize + ':' + #queryAlumniDto.name")
    public ResponseResult Page(@ModelAttribute @Validated QueryAlumniDto queryAlumniDto){
        return ResponseResult.okResult(alumniService.pageQuery(queryAlumniDto));
    }


    /**
     * 批量插入数据
     * @param importAlumniDtoList
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.ALUMNI_IMPORT+"')")
    @PostMapping("/importAlumni")
    @ApiOperation("批量插入校友信息")
    @CacheEvict(cacheNames = RedisConstant.REDIS_ALUMNI_PAGE, allEntries = true)
    public ResponseResult importAlumni(@RequestBody List<@Valid ImportAlumniDto> importAlumniDtoList){
        return alumniService.importTeacher(importAlumniDtoList);
    }

    /**
     * 导出校友信息
     * @param response
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.ALUMNI_EXPORT+"')")
    @GetMapping("/exportAlumni")
    @ApiOperation("导出校友信息")
    public void exportAlumni(HttpServletResponse response){
        try {
            // 查询信息
            List<ExportAlumniVo> userList = alumniService.getAllAlumni();

            // 读取 Excel 模板文件
            Resource templateResource = new ClassPathResource("template/用户信息.xlsx");

            // 获取输入流
            InputStream templateInputStream = templateResource.getInputStream();

            // 设置响应头
            String filename = "校友信息.xlsx";
            String encodedFilename = URLEncoder.encode(filename, "UTF-8");
            encodedFilename = new String(encodedFilename.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFilename);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // 导出到Excel
            EasyExcel.write(response.getOutputStream(), ExportAlumniVo.class).withTemplate(templateInputStream).sheet().doWrite(userList);

            // 关闭输入流
            templateInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 增加校友
     * @param addAlumniDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.ALUMNI_ADD+"')")
    @PostMapping
    @ApiOperation("增加校友")
    @CacheEvict(cacheNames = RedisConstant.REDIS_ALUMNI_PAGE, allEntries = true)
    public ResponseResult addAlumni(@RequestBody @Validated AddAlumniDto addAlumniDto){
        return alumniService.addAlumni(addAlumniDto);
    }


    /**
     * 获取校友详情
     * @param id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.ALUMNI_QUERY+"')")
    @GetMapping("/{id}")
    @ApiOperation("获取校友详情")
    public ResponseResult getAlumniById(@PathVariable Long id){
        return ResponseResult.okResult(alumniService.getAlumniById(id));
    }


    /**
     * 修改校友信息
     * @param update
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.ALUMNI_EDIT+"')")
    @PutMapping
    @ApiOperation("修改校友信息")
    @CacheEvict(cacheNames = RedisConstant.REDIS_ALUMNI_PAGE, allEntries = true)
    public ResponseResult updateAlumni(@RequestBody @Validated UpdateAlumniDto update){
        return alumniService.updateAlumni(update);
    }

    /**
     * 删除校友信息
     * @param id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.ALUMNI_REMOVE+"')")
    @DeleteMapping
    @ApiOperation("删除校友信息")
    @CacheEvict(cacheNames = RedisConstant.REDIS_ALUMNI_PAGE, allEntries = true)
    public ResponseResult removeAlumniById(@RequestParam List<Long> id){
        return alumniService.removeAlumniById(id);
    }

}
