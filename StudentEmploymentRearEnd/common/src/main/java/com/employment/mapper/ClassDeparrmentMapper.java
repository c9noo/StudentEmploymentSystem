package com.employment.mapper;

import com.employment.pojo.entity.ClassDeparrment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 49751
* @description 针对表【tb_class_deparrment】的数据库操作Mapper
* @createDate 2023-12-29 15:23:42
* @Entity com.employment.pojo.entity.ClassDeparrment
*/
@Mapper
public interface ClassDeparrmentMapper {

    void removeCLassAndDepartment(@Param("id") Long id);

    void save(@Param("id") Long id,@Param("departmentId") Long departmentId);
}
