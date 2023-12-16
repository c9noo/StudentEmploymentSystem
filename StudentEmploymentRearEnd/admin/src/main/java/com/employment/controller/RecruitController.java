package com.employment.controller;

import com.employment.constant.AuthorityConstant;
import com.employment.constant.RedisConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.pojo.dto.AddRecruitDto;
import com.employment.pojo.dto.QueryRecruitDto;
import com.employment.pojo.dto.UpdateRecruitDto;
import com.employment.pojo.dto.UpdateUserDto;
import com.employment.result.ResponseResult;
import com.employment.service.RecruitService;
import com.employment.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName RecruitController
 * @author: c9noo
 * @create: 2023-11-14 09:09
 * @Version 1.0
 * 招聘相关的接口
 **/
@RestController
@RequestMapping("/admin/recruit")
@Api(tags = "招聘相关接口")
@Slf4j
@Validated
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    /**
     * 分页查询招聘信息
     * @param queryRecruitDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+ AuthorityConstant.RECRUIT_LIST+"')")
    @GetMapping("/page")
    @ApiOperation("招聘信息分页查询")
    @Cacheable(cacheNames = RedisConstant.REDIS_RECRUIT_PAGE,
    key = "#queryRecruitDto.page + ':' + #queryRecruitDto.pageSize + ':' + #queryRecruitDto.condition + ':' + #queryRecruitDto.status")
    public ResponseResult page(@ModelAttribute @Validated QueryRecruitDto queryRecruitDto){
        return ResponseResult.okResult(recruitService.pageQuery(queryRecruitDto,null));
    }

    /**
     * 查看自己的招聘信息
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+ AuthorityConstant.RECRUIT_LIST+"')")
    @GetMapping("/myPage")
    @ApiOperation("查看自己的招聘信息")
    public ResponseResult myPage(@ModelAttribute @Validated QueryRecruitDto queryRecruitDto){
        return ResponseResult.okResult(recruitService.pageQuery(queryRecruitDto,SecurityUtil.getUserId()));
    }

    /**
     * 查询招聘信息详情
     * @param id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+ AuthorityConstant.RECRUIT_QUERY+"')")
    @GetMapping("/{id}")
    @ApiOperation("查询招聘信息详情")
    @Cacheable(cacheNames = RedisConstant.REDIS_RECRUIT_DETAIL, key = "#id")
    public ResponseResult getRecruitDetailById(@PathVariable("id") Long id){
        return ResponseResult.okResult(recruitService.getRecruitDetailById(id));
    }

    /**
     * 修改招聘状态
     * @param status 要修改的状态
     * @param id 要被修改的招聘id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.RECRUIT_EDIT+"')")
    @PostMapping("/{status}")
    @ApiOperation("修改招聘状态")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_RECRUIT_PAGE, allEntries = true),
                    @CacheEvict(cacheNames = RedisConstant.REDIS_RECRUIT_DETAIL,key = "#id")
            }
    )
    public ResponseResult stopAndStart(@PathVariable Integer status,@RequestParam Long id){
        if (status != 0 && status != 1) {
            throw new AppSystemException(AppHttpCodeEnum.PARAM_ERROR);
        }
        return recruitService.stopAndStart(status,id);
    }

    /**
     * 根据招聘id删除招聘
     * @param id 招聘id
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.RECRUIT_REMOVE+"')")
    @DeleteMapping
    @ApiOperation("删除招聘信息")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_RECRUIT_PAGE, allEntries = true),
                    @CacheEvict(cacheNames = RedisConstant.REDIS_RECRUIT_DETAIL,allEntries = true)
            }
    )
    public ResponseResult removeByIds(@RequestParam List<String> id){
        return recruitService.removeByIds(id);
    }

    /**
     * 新增招聘信息
     * @param addRecruitDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.RECRUIT_ADD+"')")
    @PostMapping
    @ApiOperation("增加招聘信息")
    @CacheEvict(cacheNames = RedisConstant.REDIS_RECRUIT_PAGE, allEntries = true)
    public ResponseResult addRecruit(@RequestBody @Validated AddRecruitDto addRecruitDto){
        if (addRecruitDto.getMinSalary()>addRecruitDto.getMaxSalary()){
            throw new AppSystemException(AppHttpCodeEnum.PARAM_ERROR);
        }
        return recruitService.addRecruit(addRecruitDto);
    }

    /**
     * 更新招聘信息
     * @param updateRecruitDto
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.RECRUIT_EDIT+"')")
    @PutMapping
    @ApiOperation("修改招聘信息")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_RECRUIT_PAGE, allEntries = true),
                    @CacheEvict(cacheNames = RedisConstant.REDIS_RECRUIT_DETAIL,allEntries = true)
            }
    )
    public ResponseResult updateRecruit(@RequestBody @Validated UpdateRecruitDto updateRecruitDto){
        return recruitService.updateRecruit(updateRecruitDto);
    }

    /**
     * 是否置顶
     * @param id
     * @param pinned
     * @return
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.RECRUIT_PINNED+"')")
    @PutMapping("/pinned/{id}")
    @ApiOperation("修改是否置顶")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = RedisConstant.REDIS_RECRUIT_PAGE, allEntries = true),
                    @CacheEvict(cacheNames = RedisConstant.REDIS_RECRUIT_DETAIL,allEntries = true)
            }
    )
    public ResponseResult updatePinned(@PathVariable Long id,@RequestParam boolean pinned){
        return recruitService.updatePinned(id,pinned);
    }
}
