package com.example.userdemo.model.service.impl;


import com.example.userdemo.model.entity.Users;
import com.example.userdemo.model.dto.UserDetailVoRequest;

import com.example.userdemo.model.repository.UserDao;
import com.example.userdemo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    @Override
    public List<Users> selectAll() {

        return userDao.findAllUsers();
    }

    @Override
    public Users selectById(int id) {

        return userDao.findById(id);
    }

    @Override
    public UserDetailVoRequest updateById(UserDetailVoRequest user) {

        Users original = userDao.findByEmail(user.getEmail());
        if (original == null && original.getEmail() == user.getEmail()) {
            Users newUsers = new Users();
            Date now = new Date();
            newUsers.setEmail(user.getEmail());
            newUsers.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUsers.setUsername(user.getUsername());
            newUsers.setUserphone(user.getUserphone());
            newUsers.setUpdatadata(now);
            userDao.updateUsers(newUsers);

            return user;
        }
        throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
    }


    @Override
    public Boolean updateaccountlocked(int id, Boolean accountnonlocked) {
        try {
            Users users = userDao.findById(id);
            users.setAccountnonlocked(accountnonlocked);
            userDao.accountnonlocked(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
