package com.employment.mapper;

import com.employment.annotation.AutoFill;
import com.employment.enums.OperationTypeEnum;
import com.employment.pojo.entity.User;
import com.employment.pojo.vo.ClassTeachVo;
import com.employment.pojo.vo.ExportUserVo;
import com.employment.pojo.vo.QueryUserVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
* @author 49751
* @description 针对表【tb_user】的数据库操作Mapper
* @createDate 2023-11-06 14:39:51
* @Entity com.employment.pojo.entity.User
*/
@Mapper
public interface UserMapper {

    /**
     * 通过用户名获取用户详情信息
     * @param username
     * @return
     */
    Optional<User> getUserByUsername(@Param("username") String username);

    /**
     * 根据用户id进行更新操作
     * @param user
     */
    @AutoFill(OperationTypeEnum.UPDATE)  //使用自定义注解对公共字段进行填充
    void updateUserById(@Param("user") User user);

    /**
     * 根据用户id进行获取用户信息
     * @param userId
     * @return
     */
    Optional<User> getUserById(@Param("userId") Long userId);

    /**
     * 根据多个用户id获取多个用户信息
     *
     * @param ids
     * @param status
     * @param name
     * @param departmentId
     * @return
     */
    Page<QueryUserVo> getUserByIdsAndStatus(@Param("ids") List<Long> ids, @Param("status") Integer status, @Param("name") String name,@Param("departmentId") Long departmentId);

    /**
     * 根据招聘id 获取名字
     * @param recruitId
     * @return
     */
    Optional<String> getNameByRecruitId(@Param("recruitId") Long recruitId);

    /**
     * 根据更新id获取名字
     * @param updateId
     * @return
     */
    String getNameById(@Param("updateId") Long updateId);

    /**
     * 新增用户信息
     * @param user
     */
    @AutoFill(OperationTypeEnum.INSERT)
    void addUser(@Param("user") User user);

    /**
     * 批量返回用户信息
     * @param ids
     * @return
     */
    List<ExportUserVo> getAllUserByIds(@Param("ids") List<Long> ids);

    /**
     * 根据用户名或是登录名查询账号
     *
     * @param name
     * @param username、
     * @return
     */
    User getUserByNameOrUsername(@Param("name") String name,@Param("username") String username);

    /**
     * 根据id查询对应的密码
     * @param id
     * @return
     */
    String getPasswordById(@Param("id") Long id);

    /**
     * 根据邮箱或是手机号查找用户
     * @param email
     * @return
     */
    User getUserByEmailOrPhone(@Param("email") String email,@Param("phone") String phone);

    /**
     * 根据邮箱更新用户密码
     * @param user
     */
    void updatePasswordByEmail(@Param("user") User user);

    /**
     * 查询老师的姓名和id
     * @param userId
     * @return
     */
    List<ClassTeachVo> selectUserNameAndId(@Param("userId") Long userId);
}
