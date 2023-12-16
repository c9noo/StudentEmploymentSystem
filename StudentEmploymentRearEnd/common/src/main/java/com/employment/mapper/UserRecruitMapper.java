package com.employment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 49751
* @description 针对表【tb_user_recruit】的数据库操作Mapper
* @createDate 2023-11-14 14:44:29
* @Entity com.employment.pojo.entity.UserRecruit
*/
@Mapper
public interface UserRecruitMapper {

    Long getUserIdByRecruitId(@Param("id") Long id);

    List<Long> getRecruitIdByUserIds(@Param("ids") List<Long> ids);

    void addUserIdAndRecruitId(@Param("userId") Long userId,@Param("recruitId") long generateId);
}
