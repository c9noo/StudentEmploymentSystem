package com.employment.controller;

import com.alibaba.excel.EasyExcel;
import com.employment.constant.AuthorityConstant;
import com.employment.constant.FileSuffixConstant;
import com.employment.constant.RedisConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.pojo.dto.AddUserDto;
import com.employment.pojo.dto.ImportUserDto;
import com.employment.pojo.dto.QueryCompanyDto;
import com.employment.pojo.dto.UpdateUserDto;
import com.employment.pojo.vo.ExportUserVo;
import com.employment.result.ResponseResult;
import com.employment.service.CompanyService;
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
import java.util.Arrays;
import java.util.List;


/**
 * @program: StudentEmploymentSystem
 * @ClassName CompanyController
 * @author: c9noo
 * @create: 2023-11-15 18:52
 * @Version 1.0
 * 企业相关操作
 **/
@RestController
@RequestMapping("/admin/company")
@Api(tags = "企业相关接口")
@Validated
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    /**
     * 分页查询企业列表
     * @param queryCompanyDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+ AuthorityConstant.COMPANY_LIST+"')")
    @GetMapping("/page")
    @ApiOperation("分页查询企业列表")
    @Cacheable(cacheNames = RedisConstant.REDIS_COMPANY_PAGE,
            key = "#queryCompanyDto.page + ':' + #queryCompanyDto.pageSize + ':' + #queryCompanyDto.name + ':' + #queryCompanyDto.status"
    )
    public ResponseResult page(@ModelAttribute @Validated QueryCompanyDto queryCompanyDto){
        return ResponseResult.okResult(companyService.pageQuery(queryCompanyDto));
    }

    /**
     * 查询企业详情
     * @param id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.COMPANY_QUERY+"')")
    @GetMapping("/{id}")
    @ApiOperation("获取企业详情")
    @Cacheable(cacheNames = RedisConstant.REDIS_COMPANY_DETAIL,key = "#id")
    public ResponseResult getCompanyById(@PathVariable Long id){
        return ResponseResult.okResult(companyService.getCompanyById(id));
    }

    /**
     * 修改企业状态
     * @param status 要修改的状态
     * @param id 要被修改的企业id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.COMPANY_EDIT+"')")
    @PostMapping("/{status}")
    @ApiOperation("修改企业状态")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_COMPANY_PAGE, allEntries = true),
                    @CacheEvict(cacheNames = RedisConstant.REDIS_COMPANY_DETAIL,key = "#id")
            }
    )
    public ResponseResult stopAndStart(@PathVariable Integer status,@RequestParam Long id){
        if (status != 0 && status != 1) {
            throw new AppSystemException(AppHttpCodeEnum.PARAM_ERROR);
        }
        return companyService.stopAndStart(status,id);
    }

    /**
     * 根据企业id删除企业
     * @param id 企业id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.COMPANY_REMOVE+"')")
    @DeleteMapping
    @ApiOperation("删除企业信息")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_COMPANY_PAGE, allEntries = true),
                    //TODO 监听Mysql binlog 进行处理缓存是最优解。当数据发生变化之后 在开始清除缓存
                    @CacheEvict(cacheNames = RedisConstant.REDIS_COMPANY_DETAIL,allEntries = true)
            }
    )
    public ResponseResult removeByIds(@RequestParam List<String> id){
        return companyService.removeByIds(id);
    }

    /**
     * 修改企业信息
     * @param updateUserDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.COMPANY_EDIT+"')")
    @PutMapping
    @ApiOperation("更新企业信息")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_COMPANY_PAGE, allEntries = true),
                    @CacheEvict(cacheNames = RedisConstant.REDIS_COMPANY_DETAIL,key = "#updateUserDto.id")
            }
    )
    public ResponseResult updateCompany(@RequestBody @Validated UpdateUserDto updateUserDto){
        return companyService.updateCompany(updateUserDto);
    }

    /**
     * 增加企业
     * @param addUserDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.COMPANY_ADD+"')")
    @PostMapping
    @ApiOperation("增加企业")
    @CacheEvict(cacheNames = RedisConstant.REDIS_COMPANY_PAGE, allEntries = true)
    public ResponseResult addCompany(@RequestBody @Validated AddUserDto addUserDto){
        return companyService.addCompany(addUserDto);
    }

    /**
     * 批量插入数据
     * @param importUserDtoList
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.COMPANY_IMPORT+"')")
    @PostMapping("/importCompany")
    @ApiOperation("批量插入企业信息")
    @CacheEvict(cacheNames = RedisConstant.REDIS_COMPANY_PAGE, allEntries = true)
    public ResponseResult importCompany(@RequestBody List<@Valid ImportUserDto> importUserDtoList){
        return companyService.importCompany(importUserDtoList);
    }


    /**
     * 导出企业信息
     * @param response
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.COMPANY_EXPORT+"')")
    @GetMapping("/exportCompany")
    @ApiOperation("导出企业信息")
    public void exportCompany(HttpServletResponse response){
        try {
            // 查询信息
            List<ExportUserVo> userList = companyService.getAllCompany();

            // 读取 Excel 模板文件
            Resource templateResource = new ClassPathResource("template/用户信息.xlsx");

            // 获取输入流
            InputStream templateInputStream = templateResource.getInputStream();

            // 设置响应头
            String filename = "企业信息.xlsx";
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

}
