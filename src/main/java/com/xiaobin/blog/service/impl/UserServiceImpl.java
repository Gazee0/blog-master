package com.xiaobin.blog.service.impl;

import com.xiaobin.blog.mapper.UserMapper;
import com.xiaobin.blog.pojo.User;
import com.xiaobin.blog.service.UserService;
import com.xiaobin.blog.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper mapper;

    @Override
    public User checkUser(String username, String password) {
        return mapper.findByUsernameAndPassword(username, MD5Utils.code(password));
    }

    @Override
    public User findByUsername(String username) {
        return mapper.findByUsername(username);
    }
}
