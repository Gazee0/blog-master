package com.xiaobin.blog.mapper;

import com.xiaobin.blog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
   public  User findByUsernameAndPassword(@Param("username")  String username,@Param("password")  String password);
   public User findByUsername(String username);
}