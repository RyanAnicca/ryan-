package com.example.userdemo.model.repository;

import com.example.userdemo.model.entity.User;
import com.example.userdemo.model.dto.UserDetailVo;


import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

    User findByEmail(String email);

    UserDetailVo findById(int id);

}
