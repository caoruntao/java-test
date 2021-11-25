package com.caort.mybatis.controller;

import com.caort.mybatis.batch.MybatisBatchOperation;
import com.caort.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Reed
 * @date 2021/9/28 上午10:17
 */
@RestController
public class UserController {
    @Autowired
    private MybatisBatchOperation batchOperation;

    @PostMapping("/user")
    public String batchSaveUser(@RequestBody List<User> userList) {
        batchOperation.batchInsert("com.caort.mybatis.mapper.UserMapper.saveUser", userList, 1000);
        return "success";
    }

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String student(@RequestParam("name") String name){
        return name;
    }
}
