package com.employment.mapper;

import com.employment.pojo.entity.Alumni;
import com.employment.pojo.vo.ExportAlumniVo;
import com.employment.pojo.vo.QueryAlumniVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
* @author 49751
* @description 针对表【tb_alumni】的数据库操作Mapper
* @createDate 2023-12-14 14:48:16
* @Entity com.employment.pojo.entity.Alumni
*/
@Mapper
public interface AlumniMapper {

    /**
     * 分页查询
     * @param name
     * @return
     */
    Page<QueryAlumniVo> pageQueryAlumni(@Param("name") String name);

    Alumni getAlumniByPhone(@Param("phone")String phone);

    void add(@Param("alumni") Alumni alumni);

    List<ExportAlumniVo> getAllAlumni();

    Optional<Alumni> getAlumniById(@Param("id") Long id);

    void updateById(@Param("alumni") Alumni alumni);

    void removeAlumniById(@Param("id") Long id);
}
