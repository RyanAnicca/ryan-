package com.example.userdemo.model.service;

import com.example.userdemo.model.entity.User;
import com.example.userdemo.model.entity.UserDetailVo;
import com.example.userdemo.model.entity.UserDetailVoRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    public UserDetails loadUserByEmail(String email);

    public UserDetailVoRequest save(UserDetailVoRequest user);

    public UserDetailVoRequest updateById(UserDetailVoRequest user);

    public UserDetailVo selectById(int id);

    public List<User> selectAll();
}
