package com.employment.mapper;

import com.employment.pojo.dto.QueryClassInfoDto;
import com.employment.pojo.vo.QueryClassInfoVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 49751
* @description 针对表【tb_classInformation】的数据库操作Mapper
* @createDate 2023-12-18 09:22:03
* @Entity com.employment.pojo.entity.ClassInformation
*/
@Mapper
public interface ClassInfoMapper {

    /**
     * 分页查询班级
     * @param name
     * @param adviser
     * @param status
     * @return
     */
    Page<QueryClassInfoVo> pageQueryClassInfo(@Param("name") String name,@Param("adviser") String adviser,@Param("status") Integer status);
}
