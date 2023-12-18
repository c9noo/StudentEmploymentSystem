package com.employment.service.impl;

import com.employment.constant.RoleKeyConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.mapper.ClassInfoMapper;
import com.employment.mapper.StudentMapper;
import com.employment.mapper.UserRoleMapper;
import com.employment.pojo.dto.AddClassDto;
import com.employment.pojo.dto.QueryClassInfoDto;
import com.employment.pojo.dto.UserContextDto;
import com.employment.pojo.entity.ClassInformation;
import com.employment.pojo.vo.ClassStudentVo;
import com.employment.pojo.vo.QueryClassDetailVo;
import com.employment.pojo.vo.QueryClassInfoVo;
import com.employment.result.PageResult;
import com.employment.result.ResponseResult;
import com.employment.service.ClassInfoService;
import com.employment.utils.SecurityUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ClassInfoServiceImpl
 * @author: c9noo
 * @create: 2023-12-18 09:29
 * @Version 1.0
 **/
@Service
public class ClassInfoServiceImpl implements ClassInfoService {

    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 分页查询学生信息
     * @param queryClassInfoDto
     * @return
     */
    @Override
    public PageResult pageQuery(QueryClassInfoDto queryClassInfoDto) {

        //设置分页
        PageHelper.startPage(queryClassInfoDto.getPage(),queryClassInfoDto.getPageSize());

        //对过滤条件进行过滤
        Optional<String> nameOptional = Optional.ofNullable(queryClassInfoDto.getName()).map(String::trim);
        Optional<String> adviser = Optional.ofNullable(queryClassInfoDto.getAdviser()).map(String::trim);

        //获取用户的角色
        UserContextDto userContext = getUserContext();

        //判断是管理员,就把id设置为null
        if (isAdmin(userContext.getRoleKeyByUserId())){
            userContext.setUserId(null);
        }

        //进行查询
        Page<QueryClassInfoVo> page = classInfoMapper.pageQueryClassInfo(
                nameOptional.orElse(null),
                adviser.orElse(null),
                queryClassInfoDto.getStatus(),
                userContext.getUserId());

        return new PageResult((int)page.getTotal(),page.getResult());
    }

    /**
     * 获取班级对应的学生信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public QueryClassDetailVo getClassById(Long id) {

        //查询出班级的信息
        QueryClassDetailVo queryClassDetailVo = classInfoMapper.getClassInfoById(id);

        //获取用户的角色
        UserContextDto userContext = getUserContext();

        //判断不是管理员
        if (!isAdmin(userContext.getRoleKeyByUserId())){
            //看这个班级的老师id是不是相同
            if (!queryClassDetailVo.getUserId().equals(userContext.getUserId())){
                throw new AppSystemException(AppHttpCodeEnum.POWER_ERROR);
            }
        }

        //查询出班级的学生信息
        List<ClassStudentVo> classStudentVoList = studentMapper.getStudentByClassId(id);

        queryClassDetailVo.setClassStudentVoList(classStudentVoList);

        return queryClassDetailVo;
    }

    /**
     * 删除班级
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResponseResult removeById(Long id) {

        //获取用户的信息
        UserContextDto userContext = getUserContext();

        //判断不是管理员
        if (!isAdmin(userContext.getRoleKeyByUserId())){
            //看这个班级的老师id是不是相同
            if (!classInfoMapper.getUserIdById(id).equals(userContext.getUserId())){
                throw new AppSystemException(AppHttpCodeEnum.POWER_ERROR);
            }
        }

        //将班级绑定的学生的classId 修改为null
        studentMapper.updateClassIdByStudentClassId(id);

        //拷贝成实体类
        ClassInformation classInformation = new ClassInformation();
        classInformation.setId(id);
        classInformation.setIsDelete(1);

        //删除班级
        classInfoMapper.updateById(classInformation);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addClassInfo(AddClassDto addClassDto) {
        return null;
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
}
