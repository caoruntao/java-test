package com.crt.dynamicdatasource.mapper;

import com.crt.dynamicdatasource.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author reed
 * @Date 2020/7/17 9:39 上午
 */
@Mapper
public interface UserMapper {
    User findUserById(long id);

    void saveUser(User user);
}
