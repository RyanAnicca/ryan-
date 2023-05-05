package com.example.userdemo.model.service;

import com.example.userdemo.model.entity.User;

import java.util.List;

public interface UserService {

    public User create(User user);

//    public void deleteByid(int id);

    public User updateById(User user);

    public User selectById(int id);

    public User selectByEmail(String  email);

    public List<User> selectAll();
}
