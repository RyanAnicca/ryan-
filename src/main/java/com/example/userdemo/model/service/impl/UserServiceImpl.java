package com.example.userdemo.model.service.impl;

import com.example.userdemo.exception.AlreadyExistsException;
import com.example.userdemo.model.entity.User;
import com.example.userdemo.model.entity.UserDTO;
import com.example.userdemo.model.repository.UserDao;
import com.example.userdemo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    public void register(UserDTO dto) {
        // 预检查用户名是否存在
        Optional<User> userOptional = this.getUserEmail(dto.getUserEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsException("Save failed, the user email already exist.");
        }
        User user = userMapper.convertOfUserRegisterDTO(dto);
        // 将登录密码进行加密
        String cryptPassword = bcryptEncoder.encode(dto.getPassword());
        user.setPassword(cryptPassword);
        try {
            userDao.save(user);
        } catch (DataIntegrityViolationException e) {
            // 如果预检查没有检查到重复，就利用数据库的完整性检查
            throw new AlreadyExistsException("Save failed, the user email already exist.");

        }
    }

    public User save(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(newUser);
    }

    public User updateById(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(newUser);
    }

    public Iterable<User> selectById(int id) {

        return userDao.findById();
    }

    public Iterable<User> selectAll() {

        return userDao.findAll();
    }


}
