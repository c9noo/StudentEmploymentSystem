package com.employment.service.impl;

import com.employment.constant.RoleKeyConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.mapper.*;
import com.employment.pojo.dto.AddUserDto;
import com.employment.pojo.dto.ImportUserDto;
import com.employment.pojo.dto.QueryTeacherDto;
import com.employment.pojo.dto.UpdateUserDto;
import com.employment.pojo.entity.Recruit;
import com.employment.pojo.entity.User;
import com.employment.pojo.vo.ExportUserVo;
import com.employment.pojo.vo.QueryCompanyVo;
import com.employment.pojo.vo.QueryTeacherVo;
import com.employment.pojo.vo.QueryUserVo;
import com.employment.result.PageResult;
import com.employment.result.ResponseResult;
import com.employment.service.DepartmentService;
import com.employment.service.TeacherService;
import com.employment.utils.BeanCopyUtil;
import com.employment.utils.SnowflakeIdUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @program: StudentEmploymentSystem
 * @ClassName TeacherServiceImpl
 * @author: c9noo
 * @create: 2023-12-12 09:50
 * @Version 1.0
 **/
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SnowflakeIdUtils snowflakeIdUtils;

    @Autowired
    private UserRecruitMapper userRecruitMapper;

    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Value("${student.user.path}")
    public String userAvatarPath;

    /**
     * 分页查询列表
     * @param queryTeacherDto
     * @return
     */
    @Override
    @Transactional
    public PageResult pageQuery(QueryTeacherDto queryTeacherDto) {

        //先查询出是老师的id,并且满足系部要求
        List<Long> ids = userRoleMapper.getIdsByRoleKey(RoleKeyConstant.TEACHER);

        //对过滤条件进行过滤
        Optional<String> nameOptional = Optional.ofNullable(queryTeacherDto.getName());
        nameOptional.ifPresent(s -> s.trim());

        //设置分页查询的页码和数据
        PageHelper.startPage(queryTeacherDto.getPage(),queryTeacherDto.getPageSize());

        //查询出对应的信息
        Page<QueryUserVo> pageVo =  userMapper.getUserByIdsAndStatus(ids,queryTeacherDto.getStatus(),nameOptional.orElseGet(() -> null),queryTeacherDto.getDepartmentId());

        return new PageResult((int)pageVo.getTotal(), pageVo.getResult());
    }

    /**
     * 批量插入数据
     * @param importUserDtoList
     * @return
     */
    @Override
    public ResponseResult importTeacher(List<ImportUserDto> importUserDtoList) {
        AtomicInteger index = new AtomicInteger(1);

        List<User> users = BeanCopyUtil.copyBeanList(importUserDtoList, User.class);
        users.stream()
                .forEach(user -> {
                    verifyTheUsernameAndLoginName(user.getName(),user.getUsername(),null,index);
                    verifyThePhoneAndEmail(user.getEmail(),user.getPhone(),null,index);
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    long generateId = snowflakeIdUtils.generateId();
                    user.setId(generateId);
                    user.setAvatar(userAvatarPath);
                });

        users.stream()
                .forEach(user -> {
                            userMapper.addUser(user);
                            userRoleMapper.addUserRole(RoleKeyConstant.TEACHER,user.getId());
                        }
                );

        return ResponseResult.okResult();
    }

    /**
     * 获取所有老师
     * @return
     */
    @Override
    public List<ExportUserVo> getAllTeacher() {
        //获取到所有的老师id
        List<Long> ids = userRoleMapper.getIdsByRoleKey(RoleKeyConstant.TEACHER);

        return userMapper.getAllUserByIds(ids);
    }

    /**
     * 新增老师
     * @param addUserDto
     * @return
     */
    @Override
    public ResponseResult addTeacher(AddUserDto addUserDto) {
        verifyTheUsernameAndLoginName(addUserDto.getName(),addUserDto.getUsername(),null,null);
        verifyThePhoneAndEmail(addUserDto.getEmail(), addUserDto.getPhone(),null,null);
        //生成雪花id
        long generateId = snowflakeIdUtils.generateId();
        //将id进行赋值随后添加
        User user = BeanCopyUtil.copyBean(addUserDto, User.class);
        user.setId(generateId);
        //将密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.addUser(user);
        //设置对应的角色权限
        userRoleMapper.addUserRole(RoleKeyConstant.TEACHER,generateId);

        return ResponseResult.okResult();
    }

    /**
     * 修改老师信息
     * @param updateUserDto
     * @return
     */
    @Override
    public ResponseResult updateTeacher(UpdateUserDto updateUserDto) {
        //判断name是否存在
        verifyTheUsernameAndLoginName(updateUserDto.getName(),null,updateUserDto.getId(),null);
        verifyThePhoneAndEmail(updateUserDto.getEmail(), updateUserDto.getPhone(),null,null);
        //拷贝成bean对象
        User user = BeanCopyUtil.copyBean(updateUserDto, User.class);

        userMapper.updateUserById(user);
        return ResponseResult.okResult();
    }

    /**
     * TODO 在删除老师的时候，系部和老师的关联表是否要删除？ 待解决
     * 删除老师信息
     * @param ids
     * @return
     */
    @Override
    public ResponseResult removeByIds(List<String> ids) {
        List<Long> collect = ids.stream()
                .map(s -> Long.valueOf(s))
                .collect(Collectors.toList());

        // 先将老师相关的招聘信息进行删除
        List<Long> recruitIds = userRecruitMapper.getRecruitIdByUserIds(collect);
        recruitIds.stream().map(id -> Recruit.builder()
                        .id(id)
                        .isDelete(1)
                        .build())
                .forEach(recruitMapper::updateRecruit);

        // 转换成user对象并批量更新用户信息
        List<User> userList = collect.stream()
                .map(id -> {
                    User user = new User();
                    user.setId(Long.valueOf(id));
                    user.setIsDelete(1);
                    return user;
                })
                .collect(Collectors.toList());

        userList.stream()
                .forEach(user -> userMapper.updateUserById(user));

        return ResponseResult.okResult();
    }

    /**
     * 修改老师状态
     * @param status
     * @param id
     * @return
     */
    @Override
    public ResponseResult stopAndStart(Integer status, Long id) {
        //封装成user对象
        User user = User.builder()
                .id(id)
                .status(status)
                .build();

        //进行持久化操作
        userMapper.updateUserById(user);

        return ResponseResult.okResult();
    }

    /**
     * 查询老师详情
     * @param id
     * @return
     */
    @Override
    public QueryTeacherVo getTeacherById(Long id) {
        // 先根据id查询到企业的信息，并且拷贝给QueryTeacherVo
        User actualUser = userMapper.getUserById(id)
                .orElseThrow(() -> new AppSystemException(AppHttpCodeEnum.DATA_IS_NULL));

        QueryTeacherVo queryTeacherVo = BeanCopyUtil.copyBean(actualUser, QueryTeacherVo.class);

        // 查出更新人的名字，并且赋值
        Long updateUserId = actualUser.getId();
        String name = userMapper.getNameById(updateUserId);
        queryTeacherVo.setUpdateUsername(name);

        //查询出对应的系部，进行赋值
        queryTeacherVo.setDepartmentName(departmentMapper.getDepartmentNameByUserId(id));

        return queryTeacherVo;
    }

    /**
     * 校验用户名、登录账号
     */
    public void verifyTheUsernameAndLoginName(String name, String username, Long id, AtomicInteger index) {
        User existingUser = userMapper.getUserByNameOrUsername(name, username);

        if (!Objects.isNull(existingUser) && !Objects.equals(existingUser.getId(), id)) {
            // 如果存在相同name或username但不同id的用户，则抛出异常
            String errorMessage  = (Objects.isNull(index) ? "已存在相同的用户名或是登录账号" : "第"+index.get()+"行已存在相同的用户名或是登录账号");
            throw new AppSystemException(AppHttpCodeEnum.CREATE_USER_ERROR, errorMessage);
        }
    }

    /**
     * 校验手机号、邮箱号
     */
    public void verifyThePhoneAndEmail(String email, String phone, Long id,AtomicInteger index) {
        User existingUser = userMapper.getUserByEmailOrPhone(email,phone);
        if (!Objects.isNull(existingUser) && !Objects.equals(existingUser.getId(), id) ) {
            // 如果存在相同phone或email但不同id的用户，则抛出异常
            String errorMessage  = (Objects.isNull(index) ? "已存在相同的手机号或是邮箱号" : "第"+index.get()+"行已存在相同的手机号或是邮箱号");
            throw new AppSystemException(AppHttpCodeEnum.CREATE_USER_ERROR, errorMessage);
        }
    }




}
