package com.employment.mapper;

import com.employment.pojo.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 49751
* @description 针对表【tb_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2023-11-09 20:54:01
* @Entity com.employment.pojo.entity.UserRole
*/
@Mapper
public interface UserRoleMapper {

    List<String> getRoleKeyByUserId(@Param("id") Long userId);

    List<Long> getIdsByRoleKey(@Param("roleKey") String roleKey);

    void addUserRole(@Param("roleKey") String roleKey,@Param("generateId") long generateId);
}
