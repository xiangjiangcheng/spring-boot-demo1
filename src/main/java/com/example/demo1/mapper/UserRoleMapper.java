package com.example.demo1.mapper;

import com.example.demo1.entity.Role;
import com.example.demo1.entity.User;
import com.example.demo1.entity.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);


    /**
     * 根据用户ID查询用户角色关系
     * @param id
     * @return
     */
    @Select("select * from tbl_user_role where user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "roleId", column = "role_id"),
            @Result(
                    property = "user",
                    column = "user_id",
                    javaType = User.class,
                    one = @One(select = "com.example.demo1.mapper.UserMapper.findByUserId")
            ),
            @Result(
                    property = "role",
                    column = "role_id",
                    javaType = Role.class,
                    one = @One(select = "com.example.demo1.mapper.RoleMapper.findById")
            )

    })
    List<UserRole> findByUserId(Integer id);
}