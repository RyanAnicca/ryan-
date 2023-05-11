package com.example.userdemo.model.service.impl;


import com.example.userdemo.model.entity.Users;
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
        Users users = userDao.findByEmail(email);
        if (users == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(users.getEmail(), users.getPassword(),
                new ArrayList<>());
    }

    public UserDetailVoRequest save(UserDetailVoRequest user) {
        Users original = userDao.findByEmail(user.getEmail());
        if (original == null) {
            Users newUsers = new Users();
            Date now = new Date();
            newUsers.setEmail(user.getEmail());
            newUsers.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUsers.setUsername(user.getUsername());
            newUsers.setUserphone(user.getUserphone());
            newUsers.setRegisterdata(now);
            newUsers.setUpdatadata(null);
            userDao.save(newUsers);

            return user;
        }
        throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
    }

    public UserDetailVoRequest updateById(UserDetailVoRequest user) {
        Users newUsers = new Users();
        Date now = new Date();
        newUsers.setEmail(user.getEmail());
        newUsers.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUsers.setUsername(user.getUsername());
        newUsers.setUserphone(user.getUserphone());
        newUsers.setRegisterdata(user.getRegisterdata());
        newUsers.setUpdatadata(now);
        userDao.save(newUsers);

        return user;
    }

    public UserDetailVo selectById(int id) {

        return userDao.findById(id);
    }

    public List<Users> selectAll() {

//        return userDao.selectAll();
        return null;
    }

    public Boolean updateaccountlocked(int id, Boolean accountnonlocked) {
        try {
//            User user = userDao.selectByPrimaryKey(id);
//            user.setAccountnonlocked(accountnonlocked);
//            userDao.updateByPrimaryKey(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
