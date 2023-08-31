package com.example.letterproject.service;

import com.example.letterproject.dao.LetterDataDao;
import com.example.letterproject.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterDateService  {
    @Autowired
    private LetterDataDao letterDataDao;
    public void init(){
        if(letterDataDao.init()>0){
            System.out.println("mysql数据库初始化成功");
        }
        else {
            System.out.println("mysql数据库初始化失败");
        }
    }

    public List<LetterStartNum> browseLetterStartNum() {
        return letterDataDao.browseLetterStartNum();
    }


    public List<LetterEndNum> browseLetterEndNum() {
        return letterDataDao.browseLetterEndNum();
    }


    public List<LetterType> browseLetterType() {
        return letterDataDao.browseLetterType();
    }


    public List<LetterToad> browseLetterToad() {
        return letterDataDao.browseLetterToad();
    }


    public List<LetterOrganization> browseLetterOrganization() {
        return letterDataDao.browseLetterOrganization();
    }
}
