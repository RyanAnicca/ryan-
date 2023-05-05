package com.example.userdemo.model.repository;

import com.example.userdemo.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {

    User findByUsername(String username);


    Iterable<User> findById();
}
