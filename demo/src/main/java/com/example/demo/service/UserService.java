package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.CartDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;

import jakarta.validation.Valid;

@Component
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    CartDao cartDao;

    // 註冊 + 新增購物車
    public Integer register(@Valid UserRegisterRequest userRegisterRequest) {
        // 檢查是否已有此帳號
        User user = userDao.getUserByUsername(userRegisterRequest.getUsername());

        if (user != null) { // 假設此帳號被註冊過
            return null;
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        return userDao.createUser(userRegisterRequest);
    }

    // 查詢
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    // 登入
    public User login(@Valid UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByUsername(userLoginRequest.getUsername());

        // 檢查帳號是否存在
        if (user == null) {
            return null;
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 檢查密碼
        if (!user.getPassword().equals(hashedPassword)) { 
            return null;
        } else {
            return user;
        }
    }

    // 刪除帳戶
    public void deleteUserById(Integer userId) {
        userDao.deleteUserById(userId);
    }

    
 

}
