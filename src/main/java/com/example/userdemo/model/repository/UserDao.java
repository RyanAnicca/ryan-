package com.example.userdemo.model.repository;

import com.example.userdemo.model.entity.Users;
import com.example.userdemo.model.dto.UserDetailVo;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {


    int save(Users users);
    Users findByEmail(String email);

    List<UserDetailVo> findByUsersName(String name);

    UserDetailVo findById(int id);

    void accountnonlocked(Users users);

    int updateUsers(Users user);

}
