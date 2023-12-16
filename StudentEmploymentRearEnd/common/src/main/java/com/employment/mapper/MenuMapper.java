package com.employment.mapper;

import com.employment.pojo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 49751
* @description 针对表【tb_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-11-09 10:04:48
* @Entity com.employment.pojo.entity.Menu
*/
@Mapper
public interface MenuMapper {


    /**
     * 根据用户id获取菜单中的权限字段
     * @param id
     * @return
     */
    List<String> getPermisByUserId(@Param("id") Long id);

    /**
     * 根据用户id查询对应的菜单信息
     * @param userId
     * @return
     */
    List<Menu> getMenusByUserId(@Param("id") Long userId);
}
