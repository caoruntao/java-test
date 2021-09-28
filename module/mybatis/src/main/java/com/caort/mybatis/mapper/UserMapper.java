package com.caort.mybatis.mapper;

import com.caort.mybatis.entity.User;

/**
 * @author Reed
 * @date 2021/9/28 上午10:08
 */
public interface UserMapper {
    void saveUser(User user);

    User findUserById(long id);
}
