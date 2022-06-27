package com.xiaobin.blog.service;

import com.xiaobin.blog.pojo.User;

public interface UserService {

    User checkUser(String username, String password);
    User findByUsername(String username);
}
