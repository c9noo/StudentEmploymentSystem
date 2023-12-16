package com.employment.controller;

import com.alibaba.excel.EasyExcel;
import com.employment.constant.AuthorityConstant;
import com.employment.constant.RedisConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.pojo.dto.AddUserDto;
import com.employment.pojo.dto.ImportUserDto;
import com.employment.pojo.dto.QueryTeacherDto;
import com.employment.pojo.dto.UpdateUserDto;
import com.employment.pojo.vo.ExportUserVo;
import com.employment.result.ResponseResult;
import com.employment.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
 * @ClassName TeacherController
 * @author: c9noo
 * @create: 2023-12-12 09:47
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/teacher")
@Api("教师管理")
@Validated
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 分页查询老师列表
     * @param queryTeacherDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.TEACHER_LIST+"')")
    @GetMapping("/page")
    @ApiOperation("招聘信息分页查询")
    @Cacheable(cacheNames = RedisConstant.REDIS_TEACHER_PAGE,
            key = "#queryTeacherDto.page + ':' + #queryTeacherDto.pageSize + ':' + #queryTeacherDto.name + ':' + #queryTeacherDto.status")
    public ResponseResult Page(@ModelAttribute @Validated QueryTeacherDto queryTeacherDto){
        return ResponseResult.okResult(teacherService.pageQuery(queryTeacherDto));
    }

    /**
     * 批量插入数据
     * @param importUserDtoList
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.TEACHER_IMPORT+"')")
    @PostMapping("/importTeacher")
    @ApiOperation("批量插入老师信息")
    @CacheEvict(cacheNames = RedisConstant.REDIS_TEACHER_PAGE, allEntries = true)
    public ResponseResult importTeacher(@RequestBody List<@Valid ImportUserDto> importUserDtoList){
        return teacherService.importTeacher(importUserDtoList);
    }

    /**
     * 导出老师信息
     * @param response
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.TEACHER_EXPORT+"')")
    @GetMapping("/exportTeacher")
    @ApiOperation("导出老师信息")
    public void exportTeacher(HttpServletResponse response){
        try {
            // 查询信息
            List<ExportUserVo> userList = teacherService.getAllTeacher();

            // 读取 Excel 模板文件
            Resource templateResource = new ClassPathResource("template/用户信息.xlsx");

            // 获取输入流
            InputStream templateInputStream = templateResource.getInputStream();

            // 设置响应头
            String filename = "教师信息.xlsx";
            String encodedFilename = URLEncoder.encode(filename, "UTF-8");
            encodedFilename = new String(encodedFilename.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFilename);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // 导出到Excel
            EasyExcel.write(response.getOutputStream(), ExportUserVo.class).withTemplate(templateInputStream).sheet().doWrite(userList);

            // 关闭输入流
            templateInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加老师
     * @param addUserDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.TEACHER_ADD+"')")
    @PostMapping
    @ApiOperation("增加老师")
    @CacheEvict(cacheNames = RedisConstant.REDIS_TEACHER_PAGE, allEntries = true)
    public ResponseResult addTeacher(@RequestBody @Validated AddUserDto addUserDto){
        return teacherService.addTeacher(addUserDto);
    }

    /**
     * 修改老师信息
     * @param updateUserDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.TEACHER_EDIT+"')")
    @PutMapping
    @ApiOperation("更新老师信息")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_TEACHER_PAGE, allEntries = true),
                    @CacheEvict(cacheNames = RedisConstant.REDIS_TEACHER_DETAIL,key = "#updateUserDto.id")
            }
    )
    public ResponseResult updateTeacher(@RequestBody @Validated UpdateUserDto updateUserDto){
        return teacherService.updateTeacher(updateUserDto);
    }

    /**
     * 根据老师id删除老师
     * @param id 老师id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.TEACHER_REMOVE+"')")
    @DeleteMapping
    @ApiOperation("删除老师信息")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_TEACHER_PAGE, allEntries = true),
                    @CacheEvict(cacheNames = RedisConstant.REDIS_TEACHER_DETAIL,allEntries = true)
            }
    )
    public ResponseResult removeByIds(@RequestParam List<String> id){
        return teacherService.removeByIds(id);
    }

    /**
     * 修改老师状态
     * @param status 要修改的状态
     * @param id 要被修改的老师id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.TEACHER_EDIT+"')")
    @PostMapping("/{status}")
    @ApiOperation("修改老师状态")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_TEACHER_PAGE, allEntries = true),
                    @CacheEvict(cacheNames = RedisConstant.REDIS_TEACHER_DETAIL,key = "#id")
            }
    )
    public ResponseResult stopAndStart(@PathVariable Integer status,@RequestParam Long id){
        if (status != 0 && status != 1) {
            throw new AppSystemException(AppHttpCodeEnum.PARAM_ERROR);
        }
        return teacherService.stopAndStart(status,id);
    }

    /**
     * 查询老师详情
     * @param id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.TEACHER_QUERY+"')")
    @GetMapping("/{id}")
    @ApiOperation("获取老师详情")
    @Cacheable(cacheNames = RedisConstant.REDIS_TEACHER_DETAIL,key = "#id")
    public ResponseResult getTeacherById(@PathVariable Long id){
        return ResponseResult.okResult(teacherService.getTeacherById(id));
    }

    /**
     * TODO 单独删除老师
     */

}
