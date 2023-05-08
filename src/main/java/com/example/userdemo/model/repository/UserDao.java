package com.example.userdemo.model.repository;

import com.example.userdemo.model.entity.User;
import com.example.userdemo.model.entity.UserDetailVo;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {

    User findByEmail(String email);


    UserDetailVo findById(int id);

}
