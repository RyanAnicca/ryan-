package com.example.userdemo.model.repository;

import com.example.userdemo.model.entity.User;
import com.example.userdemo.model.entity.UserDetailVo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

@Repository
public interface UserDao extends BaseMapper<User> {

    User findByEmail(String email);

    UserDetailVo findById(int id);

}
