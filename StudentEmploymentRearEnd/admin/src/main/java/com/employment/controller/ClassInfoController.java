package com.employment.controller;

import com.employment.constant.AuthorityConstant;
import com.employment.constant.RedisConstant;
import com.employment.pojo.dto.QueryAlumniDto;
import com.employment.pojo.dto.QueryClassInfoDto;
import com.employment.result.ResponseResult;
import com.employment.service.ClassInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation("学生信息分页查询")
    public ResponseResult Page(@ModelAttribute QueryClassInfoDto queryClassInfoDto){
        return ResponseResult.okResult(classInfoService.pageQuery(queryClassInfoDto));
    }

}
