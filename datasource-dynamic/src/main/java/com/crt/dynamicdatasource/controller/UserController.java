package com.crt.dynamicdatasource.controller;

import com.crt.dynamicdatasource.entity.User;
import com.crt.dynamicdatasource.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author reed
 * @Date 2020/7/17 9:41 上午
 */
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable long id){
        return userMapper.findUserById(id);
    }

    @PostMapping("/user")
    public long saveUser(@RequestBody User user){
        userMapper.saveUser(user);
        return user.getId();
    }
}
