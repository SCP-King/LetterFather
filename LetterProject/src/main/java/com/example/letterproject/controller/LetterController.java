package com.example.letterproject.controller;

import com.example.letterproject.pojo.*;
import com.example.letterproject.service.LetterDateService;
import com.example.letterproject.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class LetterController {
    @Autowired
    private LetterDateService letterDateService;
    @Autowired
    private UserService userService;
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/login")
    @ResponseBody
    @SneakyThrows
    public String login(String account, String password, HttpServletRequest request){
        request.getSession().setAttribute("account",account);
        return userService.login(new User(account,password));
    }


    @RequestMapping("/register")
    @ResponseBody
    @SneakyThrows
    public String register(String account,String password){
        return userService.register(new User(account,password));
    }

    @RequestMapping("/function")
    public String function(){
        return "function";
    }

    @RequestMapping("/function/changePwd")
    public String changePwd(){
        return "changePwd";
    }
    @RequestMapping("/function/data1")
    public String data1(){
        return "LetterStartNum";
    }
    @RequestMapping("/function/data2")
    public String data2(){
        return "LetterEndNum";
    }
    @RequestMapping("/function/data3")
    public String data3(){
        return "LetterType";
    }
    @RequestMapping("/function/data4")
    public String data4(){
        return "LetterToad";
    }
    @RequestMapping("/function/data5")
    public String data5(){
        return "LetterOrganization";
    }
    @RequestMapping("/reset")
    @ResponseBody
    public String reset(String account,String password,String pwd,String newPwd){
        return userService.resetPwd(account,password,pwd);
    }

    @RequestMapping("/data1")
    @ResponseBody
    public List<LetterStartNum> getData1(){
        List<LetterStartNum> letterStartNumList=letterDateService.browseLetterStartNum();
        return letterStartNumList;

    }

    @RequestMapping("/data2")
    @ResponseBody
    public List<LetterEndNum> getData2(){
        List<LetterEndNum> letterEndNumList=letterDateService.browseLetterEndNum();
        return letterEndNumList;
    }
    @RequestMapping("/data3")
    @ResponseBody
    public List<LetterType> getData3(){
        List<LetterType> letterTypeList=letterDateService.browseLetterType();
        return letterTypeList;
    }
    @RequestMapping("/data4")
    @ResponseBody
    public List<LetterToad> getData4(){
        List<LetterToad> letterToadList=letterDateService.browseLetterToad();
        return letterToadList;
    }
    @RequestMapping("/data5")
    @ResponseBody
    public List<LetterOrganization> getData5(){
        List<LetterOrganization> letterOrganizationList=letterDateService.browseLetterOrganization();
        return letterOrganizationList;
    }


}
