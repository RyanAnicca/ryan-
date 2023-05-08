package com.example.userdemo.model.repository;

import com.example.userdemo.model.entity.User;
import com.example.userdemo.model.entity.UserDetail;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailDao extends CrudRepository<UserDetail, Integer> {

}
