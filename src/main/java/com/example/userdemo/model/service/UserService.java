package com.example.userdemo.model.service;

import com.example.userdemo.model.entity.Users;
import com.example.userdemo.model.dto.UserDetailVoRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    List<Users> selectAll();

    Users selectById(int id);

    String updateById(UserDetailVoRequest user);

    Boolean updateaccountlocked(int id, Boolean accountnonlocked);

    String deleteUserById(List<Integer> ids);

}
