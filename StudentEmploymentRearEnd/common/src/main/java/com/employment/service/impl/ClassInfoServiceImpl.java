package com.employment.service.impl;

import com.employment.mapper.ClassInfoMapper;
import com.employment.pojo.dto.QueryClassInfoDto;
import com.employment.pojo.vo.QueryClassInfoVo;
import com.employment.result.PageResult;
import com.employment.service.ClassInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 分页查询学生信息
     * @param queryClassInfoDto
     * @return
     */
    @Override
    public PageResult pageQuery(QueryClassInfoDto queryClassInfoDto) {

        //设置分页
        PageHelper.startPage(queryClassInfoDto.getPage(),queryClassInfoDto.getPageSize());

//        //对过滤条件进行过滤
        Optional<String> nameOptional = Optional.ofNullable(queryClassInfoDto.getName()).map(String::trim);
        Optional<String> adviser = Optional.ofNullable(queryClassInfoDto.getAdviser()).map(String::trim);


        //进行查询
        Page<QueryClassInfoVo> page = classInfoMapper.pageQueryClassInfo(nameOptional.orElse(null),
                adviser.orElse(null),
                queryClassInfoDto.getStatus());

        return new PageResult((int)page.getTotal(),page.getResult());
    }
}
