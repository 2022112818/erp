package com.erp.mapper.auth;

import com.erp.entity.auth.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM user WHERE id = {id}")
    User findById(@Param("id") Long id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Insert("INSERT INTO user (username, password, role) VALUES (#{username}, #{password}, #{role})")
    void insert(User obj);

    @Update("UPDATE user SET username=#{username}, password=#{password}, role=#{role} WHERE id=#{id}")
    void update(User obj);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(@Param("id") Long id);
}
