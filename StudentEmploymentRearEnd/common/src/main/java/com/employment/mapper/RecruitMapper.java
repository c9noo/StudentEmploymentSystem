package com.employment.mapper;

import com.employment.annotation.AutoFill;
import com.employment.enums.OperationTypeEnum;
import com.employment.pojo.entity.Recruit;
import com.employment.pojo.vo.QueryRecruitVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
* @author 49751
* @description 针对表【tb_recruit(招聘信息表)】的数据库操作Mapper
* @createDate 2023-11-15 09:46:18
* @Entity com.employment.pojo.entity.Recruit
*/
@Mapper
public interface RecruitMapper {

    Optional<Recruit> getRecruitById(@Param("id") Long id);

    Page<QueryRecruitVo> pageQuery(@Param("condition") String condition,@Param("status") Integer status,@Param("ids") List<Long> ids);

    /**
     * 修改招聘信息
     * @param recruit
     */
    @AutoFill(OperationTypeEnum.UPDATE)
    void updateRecruit(@Param("recruit") Recruit recruit);

    @AutoFill(OperationTypeEnum.INSERT)
    void addRecruit(@Param("recruit") Recruit recruit);

    int getPinnedCount();
}
