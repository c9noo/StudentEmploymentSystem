package com.employment.service.impl;

import com.employment.constant.RoleKeyConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.mapper.AlumniMapper;
import com.employment.pojo.dto.AddAlumniDto;
import com.employment.pojo.dto.ImportAlumniDto;
import com.employment.pojo.dto.QueryAlumniDto;
import com.employment.pojo.entity.Alumni;
import com.employment.pojo.entity.User;
import com.employment.pojo.vo.ExportAlumniVo;
import com.employment.pojo.vo.QueryAlumniDetailVo;
import com.employment.pojo.vo.QueryAlumniVo;
import com.employment.result.PageResult;
import com.employment.result.ResponseResult;
import com.employment.service.AlumniService;
import com.employment.utils.BeanCopyUtil;
import com.employment.utils.SnowflakeIdUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AlumniServiceImpl
 * @author: c9noo
 * @create: 2023-12-14 14:56
 * @Version 1.0
 **/
@Service
@Slf4j
public class AlumniServiceImpl implements AlumniService {

    @Autowired
    private AlumniMapper alumniMapper;

    @Autowired
    private SnowflakeIdUtils snowflakeIdUtils;

    /**
     * 分页查询
     * @param queryAlumniDto
     * @return
     */
    @Override
    public PageResult pageQuery(QueryAlumniDto queryAlumniDto) {

        PageHelper.startPage(queryAlumniDto.getPage(),queryAlumniDto.getPageSize());

        //对过滤条件进行过滤
        Optional<String> nameOptional = Optional.ofNullable(queryAlumniDto.getName());
        nameOptional.ifPresent(s -> s.trim());

        Page<QueryAlumniVo> page = alumniMapper.pageQueryAlumni(nameOptional.orElseGet(() -> null));

        return new PageResult((int)page.getTotal(), page.getResult());
    }

    /**
     * 批量增加校友
     * @param importAlumniDtoList
     * @return
     */
    @Override
    public ResponseResult importTeacher(List<ImportAlumniDto> importAlumniDtoList) {
        AtomicInteger index = new AtomicInteger(1);

        List<String> collect = importAlumniDtoList.stream()
                .map(importAlumniDto -> importAlumniDto.getGraduationDate()).collect(Collectors.toList());

        //拷贝成实体类
        List<Alumni> alumni = BeanCopyUtil.copyBeanList(importAlumniDtoList, Alumni.class);


        //校验手机号是否重复
        alumni.stream()
                .forEach(user -> {
                    verifyThePhone(user.getPhone(),index);
                    user.setId(snowflakeIdUtils.generateId());
                    user.setLastUpdateTime(LocalDate.now());
                    user.setIsDelete(0);
                    // 定义日期格式化模式
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");
                    // 将字符串解析为 LocalDate
                    LocalDate localDate = LocalDate.parse(collect.iterator().next(), formatter);
                    user.setGraduationDate(localDate);
                });
        alumni.stream()
                .forEach(user -> {
                    alumniMapper.add(user);
                });

        return ResponseResult.okResult();
    }

    /**
     * 获取校友
     * @return
     */
    @Override
    public List<ExportAlumniVo> getAllAlumni() {
        return  alumniMapper.getAllAlumni();
    }

    @Override
    public ResponseResult addAlumni(AddAlumniDto addAlumniDto) {

        //拷贝成实体类
        Alumni alumni = BeanCopyUtil.copyBean(addAlumniDto, Alumni.class);

        //设置空缺的字段
        alumni.setId(snowflakeIdUtils.generateId());
        alumni.setIsDelete(0);
        alumni.setLastUpdateTime(LocalDate.now());

        //持久化操作
        alumniMapper.add(alumni);

        return ResponseResult.okResult();
    }


    /**
     * 获取校友详情
     * @param id
     * @return
     */
    @Override
    public QueryAlumniDetailVo getAlumniById(Long id) {
        // 先根据id查询到校友的信息，并且拷贝给QueryAlumniDetailVo
        Alumni alumni = alumniMapper.getAlumniById(id)
                .orElseThrow(() -> new AppSystemException(AppHttpCodeEnum.DATA_IS_NULL));
        return BeanCopyUtil.copyBean(alumni,QueryAlumniDetailVo.class);
    }

    /**
     * 校验手机号
     * @param phone
     * @param index
     */
    private void verifyThePhone(String phone, AtomicInteger index) {
        Alumni verifyAlumni = alumniMapper.getAlumniByPhone(phone);
        if (!Objects.isNull(verifyAlumni)) {
            // 如果存在相同phone则抛出异常
            String errorMessage  = (Objects.isNull(index) ? "已存在相同的手机号" : "第"+index.get()+"行已存在相同的手机号");
            throw new AppSystemException(AppHttpCodeEnum.CREATE_USER_ERROR, errorMessage);
        }
    }
}
