package com.employment.mapper;

import com.employment.pojo.entity.Department;
import com.employment.pojo.vo.DepartmentTagVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 49751
* @description 针对表【tb_department】的数据库操作Mapper
* @createDate 2023-12-28 10:38:34
* @Entity com.employment.pojo.entity.Department
*/
@Mapper
public interface DepartmentMapper {

    /**
     * 查询系部标签
     * @return
     */
    List<DepartmentTagVo> getDepartment();

    String getDepartmentNameByUserId(@Param("id") Long id);

    /**
     * 根据班级id 获取到对应的系部名称
     * @param id
     * @return
     */
    String getDepartmentNameByClassId(@Param("id") Long id);
}
