package com.example.letterproject.dao;

import com.example.letterproject.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    User login(User user);
    int register(User user);

    int resetPwd(@Param("account") String account,@Param("password") String password,@Param("pwd") String pwd);


}
