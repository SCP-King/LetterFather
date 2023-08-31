package com.example.letterproject.service;

import com.example.letterproject.dao.UserDao;
import com.example.letterproject.pojo.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;

@Service
public class UserService{
    @SneakyThrows
    private String MD5Pwd(String pwd){
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pwd.getBytes());
        String Hpwd=new BigInteger(1,md.digest()).toString(16);
        return Hpwd;
    }
    @Autowired
    private UserDao userDao;
    public String login(User user) {
        user.setPassword(MD5Pwd(user.getPassword()));
        User res=userDao.login(user);
        if(res==null){
            return "error1";
        }
        else if (!user.getPassword().equals(res.getPassword())){
            return "error2";
        }
        else return "success";
    }


    public String register(User user) {
        user.setPassword(MD5Pwd(user.getPassword()));
        if(userDao.register(user)>0){
            return "success";
        }
        else return "error";
    }


    public String resetPwd(String account, String password, String pwd) {
        if(userDao.resetPwd(account, MD5Pwd(password), MD5Pwd(pwd))>0){
            return "success";
        }
        else return "error";
    }
}
