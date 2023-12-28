package com.employment.controller;

import com.employment.constant.AuthorityConstant;
import com.employment.result.ResponseResult;
import com.employment.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: StudentEmploymentSystem
 * @ClassName DepartmentController
 * @author: c9noo
 * @create: 2023-12-28 10:41
 * @Version 1.0
 * 系部管理的controller
 **/
@RestController
@RequestMapping("/admin/department")
@Api("系部管理")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询系部名和对应的id
     */
    @PreAuthorize("@Permission.hasPermission('"+ AuthorityConstant.DEPARTMENT_LIST+"')")
    @GetMapping
    @ApiOperation("查询系部名和对应的id")
    public ResponseResult departmentTag(){
        return ResponseResult.okResult(departmentService.getDepartment());
    }
}
