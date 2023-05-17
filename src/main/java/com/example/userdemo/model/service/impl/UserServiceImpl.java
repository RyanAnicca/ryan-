package com.example.userdemo.model.service.impl;


import com.example.userdemo.model.entity.Users;
import com.example.userdemo.model.dto.UserDetailVoRequest;

import com.example.userdemo.model.repository.TokenRepository;
import com.example.userdemo.model.repository.UserDao;
import com.example.userdemo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    @Autowired
    public TokenRepository tokenRepository;

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
    public String updateById(UserDetailVoRequest user) {
        Users original = userDao.findByEmail(user.getEmail());

        if (original != null) {
            if (!original.getUpdatadata().equals(user.getUpdatadata())) {
                // 日期不同，返回自定义信息给前端
                return "帳號已被其他人修改";
            } else {
                // 日期相同，判断邮箱相同，直接返回"此帐号已有人使用"
                if (original.getEmail().equals(user.getEmail())) {
                    return "此帳號已有人使用";
                }
                // 日期相同，继续进行其他操作
                Users newUsers = new Users();
                Date now = new Date();
                newUsers.setEmail(user.getEmail());
                newUsers.setPassword(bcryptEncoder.encode(user.getPassword()));
                newUsers.setUsername(user.getUsername());
                newUsers.setUserphone(user.getUserphone());
                newUsers.setUpdatadata(now);
                userDao.updateUsers(newUsers);

                return "修改成功!";
            }
        } else {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " does not exist");
        }
    }


    @Override
    public Boolean updateaccountlocked(int id, Boolean accountnonlocked) {
        try {
            Users users = userDao.findById(id);
            System.out.println(id);
            users.setAccountnonlocked(accountnonlocked);
            System.out.println(users);
            userDao.updateaccountlocked(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void deleteUserById(List<Integer> ids) {
        tokenRepository.deleteToken(ids);
        userDao.deleteById(ids);
    }

}
