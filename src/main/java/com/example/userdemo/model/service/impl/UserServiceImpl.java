package com.example.userdemo.model.service.impl;


import com.example.userdemo.model.entity.User;
import com.example.userdemo.model.entity.UserDetail;
import com.example.userdemo.model.entity.UserDetailVo;
import com.example.userdemo.model.entity.UserDetailVoRequest;
import com.example.userdemo.model.repository.UserDao;
import com.example.userdemo.model.repository.UserDetailDao;
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
    public UserDetailDao userDetailDao;

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
        if (original==null){
            User newUser = new User();
            Date now = new Date();
            UserDetail newUserDetail = new UserDetail();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            userDao.save(newUser);
            newUserDetail.setUsername(user.getUsername());
            newUserDetail.setUserphone(user.getUserphone());
            newUserDetail.setRegisterdata(now);
            newUserDetail.setUpdatadata(null);
            userDetailDao.save(newUserDetail);

            return user;
        }
        throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
    }

    public UserDetailVoRequest updateById(UserDetailVoRequest user) {
        User newUser = new User();
        Date now = new Date();
        UserDetail newUserDetail = new UserDetail();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        userDao.save(newUser);
        newUserDetail.setUsername(user.getUsername());
        newUserDetail.setUserphone(user.getUserphone());
        newUserDetail.setRegisterdata(user.getRegisterdata());
        newUserDetail.setUpdatadata(now);
        userDetailDao.save(newUserDetail);

        return user;
    }

    public UserDetailVo selectById(int id) {

        return userDao.findById(id);
    }

    public List<UserDetailVo> selectAll() {

        return userDao.findAll();
    }


}
