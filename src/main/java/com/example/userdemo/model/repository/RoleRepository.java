package com.example.userdemo.model.repository;

import com.example.userdemo.model.entity.Role;
import com.example.userdemo.model.entity.User;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Optional;

public interface RoleRepository extends BaseMapper<User> {
    Optional<Role> findByName(String name);
}
