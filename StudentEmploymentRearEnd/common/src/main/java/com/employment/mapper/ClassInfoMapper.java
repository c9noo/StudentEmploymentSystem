package com.employment.mapper;

import com.employment.pojo.dto.ClassIdAndTime;
import com.employment.pojo.entity.ClassInformation;
import com.employment.pojo.vo.QueryClassDetailVo;
import com.employment.pojo.vo.QueryClassInfoVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     *
     * @param name
     * @param status
     * @param descriptionId
     * @return
     */
    Page<QueryClassInfoVo> pageQueryClassInfo(@Param("name") String name, @Param("adviser") String adviser, @Param("status") Integer status, @Param("id") Long id, @Param("descriptionId") Long descriptionId);

    /**
     * 获取班级的详情信息
     * @param id
     * @return
     */
    QueryClassDetailVo getClassInfoById(@Param("id") Long id);

    /**
     * 删除班级
     * @param classInformation
     */
    void updateById(@Param("classInformation") ClassInformation classInformation);

    /**
     * 根据班级id，获取到user_id
     * @param id
     * @return
     */
    Long getUserIdById(@Param("id") Long id);

    /**
     * 保存班级信息
     * @param classInformation
     */
    void save(@Param("classInformation") ClassInformation classInformation);


    /**
     * 查询班级id 和毕业时间 和状态
     * @return
     */
    List<ClassIdAndTime> selectIdAndTime(@Param("status") Integer status);
}
