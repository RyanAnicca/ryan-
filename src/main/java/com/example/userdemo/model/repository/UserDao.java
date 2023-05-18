package com.example.userdemo.model.repository;

import com.example.userdemo.model.entity.Users;
import com.example.userdemo.model.dto.UserDetailVo;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {


    int save(Users users);
    Users findByEmail(String email);

    //模糊查詢
    List<UserDetailVo> findByUsersName(String name);

    Users findById(int id);

    int updateaccountlocked(Users users);

    int updateUsers(Users users);

    List<Users> findAllUsers();

    int deleteById(List<Integer> ids);

    List<Integer> selectByList(List<Integer> ids);

}
