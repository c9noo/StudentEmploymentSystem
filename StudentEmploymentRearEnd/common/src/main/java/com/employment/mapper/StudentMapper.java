package com.employment.mapper;

import com.employment.pojo.entity.Student;
import com.employment.pojo.vo.ClassStudentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 49751
* @description 针对表【tb_student】的数据库操作Mapper
* @createDate 2023-12-18 14:37:47
* @Entity com.employment.pojo.entity.Student
*/
@Mapper
public interface StudentMapper {

    /**
     * 获取班级对应的学生信息
     * @param id
     * @return
     */
    List<ClassStudentVo> getStudentByClassId(@Param("id") Long id);
}
