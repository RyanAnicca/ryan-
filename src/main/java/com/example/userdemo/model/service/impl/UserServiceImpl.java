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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        Users original = userDao.findById(user.getId());
        String newEmail = user.getEmail();
        Users existingUser = userDao.findByEmail(newEmail);

        if (existingUser == null || existingUser.getId() == original.getId()) {
            // 如果新的帐号在数据库中不存在或者为原始帐号本身，可以进行其他操作
            if (!original.getUpdatadata().equals(user.getUpdatadata())) {

                // 日期不同，返回自定义信息给前端
                return "帳號已被其他人修改";
            } else {
                // 日期相同，继续进行其他操作
                Users newUsers = new Users();
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = sdf.format(now);

                try {
                    Date parsedDate = sdf.parse(formattedDate);
                    Timestamp timestamp = new Timestamp(parsedDate.getTime());
                    newUsers.setId(user.getId());
                    newUsers.setEmail(user.getEmail());
                    newUsers.setPassword(bcryptEncoder.encode(user.getPassword()));
                    newUsers.setUsername(user.getUsername());
                    newUsers.setUserphone(user.getUserphone());
                    newUsers.setUpdatadata(timestamp);
                    userDao.updateUsers(newUsers);
                    return "修改成功!";
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
    }


    @Override
    public Boolean updateaccountlocked(int id, Boolean accountnonlocked) {
        try {
            Users users = userDao.findById(id);
            users.setAccountnonlocked(accountnonlocked);
            userDao.updateaccountlocked(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String deleteUserById(List<Integer> ids) {
        List<Integer> deletedids = new ArrayList<>();
        List<Integer> listuser = userDao.selectByList(ids);

        for (Integer deletId : ids) {
            if (!listuser.contains(deletId)) {
                deletedids.add(deletId);
            }
        }
        if (deletedids.size() > 0) {
            return "編號" + deletedids + "已被刪除，請重新選擇";
        }

        tokenRepository.deleteToken(ids);
        userDao.deleteById(ids);
        return "刪除成功";
    }

}
