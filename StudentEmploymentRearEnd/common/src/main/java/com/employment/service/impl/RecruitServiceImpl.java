package com.employment.service.impl;

import com.employment.constant.RoleKeyConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.mapper.RecruitMapper;
import com.employment.mapper.UserMapper;
import com.employment.mapper.UserRecruitMapper;
import com.employment.mapper.UserRoleMapper;
import com.employment.pojo.dto.AddRecruitDto;
import com.employment.pojo.dto.QueryRecruitDto;
import com.employment.pojo.dto.UpdateRecruitDto;
import com.employment.pojo.dto.UserContextDto;
import com.employment.pojo.entity.Recruit;
import com.employment.pojo.entity.User;
import com.employment.pojo.vo.QueryRecruitDetailVo;
import com.employment.pojo.vo.QueryRecruitVo;
import com.employment.result.PageResult;
import com.employment.result.ResponseResult;
import com.employment.service.RecruitService;
import com.employment.utils.BeanCopyUtil;
import com.employment.utils.SecurityUtil;
import com.employment.utils.SnowflakeIdUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @program: StudentEmploymentSystem
 * @ClassName RecruitServiceImpl
 * @author: c9noo
 * @create: 2023-11-14 14:48
 * @Version 1.0
 * 招聘信息service的实现类
 **/
@Service
public class RecruitServiceImpl implements RecruitService {

    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private UserRecruitMapper userRecruitMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private SnowflakeIdUtils snowflakeIdUtils;

    private static final int PINNED_COUNT = 5;

    /**
     * 根据招聘id，查询招聘信息详情
     * @param id
     * @return
     */
    @Override
    public QueryRecruitDetailVo getRecruitDetailById(Long id) {
        //获取到招聘人的id
        Long userIdByRecruitId = userRecruitMapper.getUserIdByRecruitId(id);

        //通过id获取到user详情
        User user = userMapper.getUserById(userIdByRecruitId)
                .orElseThrow(() -> new AppSystemException(AppHttpCodeEnum.NULL_ERROR));

        // 获取招聘详情信息，如果为null返回异常
        Recruit recruit = recruitMapper.getRecruitById(id)
                .orElseThrow(() -> new AppSystemException(AppHttpCodeEnum.NULL_ERROR));


        // 使用 BeanCopyUtil 创建 QueryRecruitDetailVo
        QueryRecruitDetailVo queryRecruitDetailVo = BeanCopyUtil.copyBean(recruit, QueryRecruitDetailVo.class);

        //设置招聘人的名字和头像
        queryRecruitDetailVo.setCompanyName(user.getName());
        queryRecruitDetailVo.setCompanyAvatar(user.getAvatar());

        return queryRecruitDetailVo;
    }

    /**
     * 分页查询招聘信息
     * @param queryRecruitDto
     * @return
     */
    @Override
    @Transactional
    public PageResult pageQuery(QueryRecruitDto queryRecruitDto,Long userId) {

        UserContextDto userContext = getUserContext();

        if (userContext.getRoleKeyByUserId().contains(RoleKeyConstant.ADMIN)){
            userId = null;
        }

        // 为避免前后空格导致查询结果，去除前后空格，并且重新赋值
        Optional.ofNullable(queryRecruitDto.getCondition())
                .map(String::trim)
                .ifPresent(queryRecruitDto::setCondition);

        List<Long> recruitIds = userRecruitMapper.getRecruitIdByUserIds(Collections.singletonList(userId));

        // 设置要查询的页码和每页的记录数
        PageHelper.startPage(queryRecruitDto.getPage(), queryRecruitDto.getPageSize());

        // 进行查询，返回的结果是一个集合
        Page<QueryRecruitVo> pageVo = recruitMapper.pageQuery(queryRecruitDto.getCondition(), queryRecruitDto.getStatus(), recruitIds);

        // 使用Stream和Lambda表达式简化代码
        pageVo.getResult().forEach(recruitVo -> recruitVo.setName(
                userMapper.getNameByRecruitId(recruitVo.getId())
                        .orElseThrow(() -> new AppSystemException(AppHttpCodeEnum.SYSTEM_ERROR))
        ));

        // 进行封装返回
        return new PageResult((int)pageVo.getTotal(), pageVo.getResult());
    }

    /**
     * 修改招聘信息状态
     * @param status
     * @param id
     * @return
     */
    @Override
    public ResponseResult stopAndStart(Integer status, Long id) {
        UserContextDto userContext = getUserContext();

        Recruit recruit = Recruit.builder()
                .id(id)
                .status(status)
                .build();

        if (isAdmin(userContext.getRoleKeyByUserId()) || isUserAuthorized(userContext.getUserId(), id)) {
            recruitMapper.updateRecruit(recruit);
            return ResponseResult.okResult();
        }

        return new ResponseResult().error(AppHttpCodeEnum.POWER_ERROR.getCode(), AppHttpCodeEnum.POWER_ERROR.getMsg());
    }

    /**
     * 根据id删除招聘信息
     * @param ids
     * @return
     */
    @Override
    public ResponseResult removeByIds(List<String> ids) {
        UserContextDto userContext = getUserContext();
        List<Long> collect = ids.stream().map(Long::valueOf).collect(Collectors.toList());

        List<Long> authorizedIds;

        if (isAdmin(userContext.getRoleKeyByUserId())) {
            authorizedIds = collect;
        } else {
            authorizedIds = collect.stream()
                    .filter(id -> isUserAuthorized(userContext.getUserId(), id))
                    .collect(Collectors.toList());

            if (authorizedIds.isEmpty()) {
                return new ResponseResult().error(AppHttpCodeEnum.POWER_ERROR.getCode(), AppHttpCodeEnum.POWER_ERROR.getMsg());
            }
        }

        List<Recruit> recruitsToUpdate = authorizedIds.stream()
                .map(id -> Recruit.builder()
                        .id(id)
                        .isDelete(1)
                        .build())
                .collect(Collectors.toList());

        recruitsToUpdate.forEach(recruitMapper::updateRecruit);

        return ResponseResult.okResult();
    }

    /**
     * 新增招聘
     * @param addRecruitDto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult addRecruit(AddRecruitDto addRecruitDto) {

        //生成雪花id
        long generateId = snowflakeIdUtils.generateId();

        //赋值给实体类
        Recruit recruit = BeanCopyUtil.copyBean(addRecruitDto, Recruit.class);
        recruit.setIsPinned(false);
        recruit.setId(generateId);

        //先设置关联表在设置招聘表
        userRecruitMapper.addUserIdAndRecruitId(SecurityUtil.getUserId(),generateId);
        recruitMapper.addRecruit(recruit);
        return ResponseResult.okResult();
    }

    /**
     * 更新招聘信息
     * @param updateRecruitDto
     * @return
     */
    @Override
    public ResponseResult updateRecruit(UpdateRecruitDto updateRecruitDto) {

        UserContextDto userContext = getUserContext();
        //如果不是 管理员 并且 这个文章也不是自己的
        if ((!isAdmin(userContext.getRoleKeyByUserId())) && (!isUserAuthorized(userContext.getUserId(),updateRecruitDto.getId()))){
            return new ResponseResult().error(AppHttpCodeEnum.POWER_ERROR.getCode(), AppHttpCodeEnum.POWER_ERROR.getMsg());
        }

        recruitMapper.updateRecruit(BeanCopyUtil.copyBean(updateRecruitDto, Recruit.class));

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updatePinned(Long id, boolean pinned) {

        //如果是要置顶
        if (pinned) {
            //查询数据库的置顶数量，不允许超过五条
            int count = recruitMapper.getPinnedCount();
            if (count>=PINNED_COUNT){
                return new ResponseResult().error(AppHttpCodeEnum.PINNED_ERROR.getCode(), AppHttpCodeEnum.PINNED_ERROR.getMsg());
            }
        }

        Recruit build = Recruit.builder().id(id).isPinned(pinned).build();
        recruitMapper.updateRecruit(build);
        return ResponseResult.okResult();
    }

    /**
     * 封装用户角色的关键字和用户的id
     * @return
     */
    private UserContextDto getUserContext() {
        Long userId = SecurityUtil.getUserId();
        List<String> roleKeyByUserId = userRoleMapper.getRoleKeyByUserId(userId);
        return new UserContextDto(userId, roleKeyByUserId);
    }


    /**
     * 判断是不是管理员
     * @param roles
     * @return
     */
    private boolean isAdmin(List<String> roles) {
        return roles.contains(RoleKeyConstant.ADMIN);
    }

    /**
     * 判断文章的id是否是当前用户
     * @param userId
     * @param recruitId
     * @return
     */
    private boolean isUserAuthorized(Long userId, Long recruitId) {
        Long userIdByRecruitId = userRecruitMapper.getUserIdByRecruitId(recruitId);
        return userId.equals(userIdByRecruitId);
    }
}
