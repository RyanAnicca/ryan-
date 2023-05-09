package com.example.userdemo.model.service.impl;


import com.example.userdemo.model.entity.User;
import com.example.userdemo.model.dto.UserDetailVo;
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
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public UserDetailVoRequest save(UserDetailVoRequest user) {
        User original = userDao.findByEmail(user.getEmail());
        if (original == null) {
            User newUser = new User();
            Date now = new Date();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setUsername(user.getUsername());
            newUser.setUserphone(user.getUserphone());
            newUser.setRegisterdata(now);
            newUser.setUpdatadata(null);
            userDao.insert(newUser);

            return user;
        }
        throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
    }

    public UserDetailVoRequest updateById(UserDetailVoRequest user) {
        User newUser = new User();
        Date now = new Date();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());
        newUser.setUserphone(user.getUserphone());
        newUser.setRegisterdata(user.getRegisterdata());
        newUser.setUpdatadata(now);
        userDao.insert(newUser);

        return user;
    }

    public UserDetailVo selectById(int id) {

        return userDao.findById(id);
    }

    public List<User> selectAll() {

        return userDao.selectAll();
    }


}
