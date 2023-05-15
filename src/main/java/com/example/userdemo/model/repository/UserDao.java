package com.example.userdemo.model.repository;

import com.example.userdemo.model.entity.Users;
import com.example.userdemo.model.dto.UserDetailVo;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {


    int save(Users users);
    Users findByEmail(String email);

    UserDetailVo findById(int id);

}
