package com.employment.controller;

import com.employment.constant.AuthorityConstant;
import com.employment.pojo.dto.QueryClassInfoDto;
import com.employment.result.ResponseResult;
import com.employment.service.ClassInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ClassInfoController
 * @author: c9noo
 * @create: 2023-12-18 09:26
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/classInfo")
@Api("学生管理管理")
public class ClassInfoController {

    @Autowired
    private ClassInfoService classInfoService;


    /**
     * 分页查询学生信息
     * @param queryClassInfoDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+ AuthorityConstant.CLASS_LIST+"')")
    @GetMapping("/page")
    @ApiOperation("班级信息分页查询")
    public ResponseResult Page(@ModelAttribute QueryClassInfoDto queryClassInfoDto){
        return ResponseResult.okResult(classInfoService.pageQuery(queryClassInfoDto));
    }

    /**
     * 获取班级详情信息
     * @param id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.CLASS_QUERY+"')")
    @GetMapping("/{id}")
    @ApiOperation("获取班级详情")
    public ResponseResult getStudentById(@PathVariable Long id){
        return ResponseResult.okResult(classInfoService.getClassById(id));
    }

    /**
     * 删除班级
     * @param id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.CLASS_REMOVE+"')")
    @DeleteMapping
    @ApiOperation("删除班级信息")
    public ResponseResult removeByIds(@RequestParam Long id){
        return classInfoService.removeById(id);
    }

}
