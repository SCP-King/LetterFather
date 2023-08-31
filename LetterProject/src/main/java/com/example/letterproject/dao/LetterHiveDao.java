package com.example.letterproject.dao;

import com.example.letterproject.utils.LetterHive;

import java.sql.ResultSet;

public class LetterHiveDao{
    private LetterHive letterHive;

    public LetterHiveDao() {
        this.letterHive = new LetterHive();
    }


    public void createTable() {
        letterHive.createTable();
        letterHive.closeConnection();
    }


    public ResultSet selectLetter(String sql, String... p) {
        return letterHive.queryTable(sql,p);
    }


    public Boolean updateLetter(String sql,String... p) {
        return letterHive.updateTable(sql,p);
    }
    public void close(){
        letterHive.closeConnection();
    }
}
