package com.example.letterproject.service;

import com.example.letterproject.dao.LetterHiveDao;

public class LetterHiveService {
    private LetterHiveDao letterHiveDao;

    public LetterHiveService() {
        this.letterHiveDao = new LetterHiveDao();
    }
    public void createLetterTable(){
        letterHiveDao.createTable();
    }

    public void loadLetter(String inpath) {
        String sql="load data inpath ? overwrite into table letters";
        letterHiveDao.updateLetter(sql,inpath);
        System.out.println("导入成功");
    }
    public void selectLetterByStartTime(){
        letterHiveDao.updateLetter("drop table letter_start_num");
        String sql="create table if not exists letter_start_num row format delimited fields terminated by '\t' stored as textfile as " +
                "select year(letterDate) as year,month(letterDate) as month,count(*) as letter_num from letters group by year(letterDate),month(letterDate) order by year(letterDate),month(letterDate)";
        letterHiveDao.updateLetter(sql);
        System.out.println("查询成功");
    }
    public void selectLetterByEndTime(){
        letterHiveDao.updateLetter("drop table letter_end_num");
        String sql= "create table if not exists letter_end_num row format delimited fields terminated by '\t' stored as textfile as " +
                "select year(replyDate) as year,month(replyDate) as month,count(*) as letter_num from letters group by year(replyDate),month(replyDate) order by year(replyDate),month(replyDate)";
        letterHiveDao.updateLetter(sql);
        System.out.println("查询成功");
    }
    public void selectLetterByToad(){
        letterHiveDao.updateLetter("drop table letter_toad");
        String sql= "create table if not exists letter_toad row format delimited fields terminated by '\t' stored as textfile as " +
                "select datediff(replyDate,letterDate) as toad,count(*) as toad_num from letters group by datediff(replyDate,letterDate) order by datediff(replyDate,letterDate)";
        letterHiveDao.updateLetter(sql);
        System.out.println("查询成功");
    }
    public void selectLetterByOrganization(){
        letterHiveDao.updateLetter("drop table letter_organization");
        String sql= "create table if not exists letter_organization row format delimited fields terminated by '\t' stored as textfile as " +
                "select replyOrganization as organization,count(*) as letter_num from letters group by replyOrganization order by replyOrganization";
        letterHiveDao.updateLetter(sql);
        System.out.println("查询成功");
    }
    public void selectLetterByType(){
        letterHiveDao.updateLetter("drop table letter_type");
        String sql= "create table if not exists letter_type row format delimited fields terminated by '\t' stored as textfile as " +
                "select letterType as type,count(*) as letter_num from letters group by letterType order by letterType";
        letterHiveDao.updateLetter(sql);
        System.out.println("查询成功");
    }

}
