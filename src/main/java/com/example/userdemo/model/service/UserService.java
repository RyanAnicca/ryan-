package com.example.userdemo.model.service;

import com.example.userdemo.model.entity.User;

public interface UserService {

    public User save(User user);

    public User updateById(User user);

    public Iterable<User> selectById(int id);

    public Iterable<User> selectAll();
}
