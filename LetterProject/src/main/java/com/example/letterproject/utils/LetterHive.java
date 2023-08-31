package com.example.letterproject.utils;

import com.example.letterproject.pojo.Letter;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LetterHive {
    private  String hiveUrl="jdbc:hive2://billsaifu:10000/school";
    private  String username="bill";
    private   Connection connection;
    private  PreparedStatement preparedStatement;
    @SneakyThrows
    public void getConnection(){
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        connection= DriverManager.getConnection(hiveUrl,username,null);
    }
    @SneakyThrows
    public void createTable(){
        getConnection();
        String sql="create table if not exists letters (  " +
                "originalId STRING," +
                "letterType STRING," +
                "letterTitle STRING," +
                "letterRecipient STRING," +
                "letterDate STRING," +
                "letterContent STRING," +
                "replyOrganization STRING," +
                "replyDate STRING," +
                "replyContent STRING)" +
                "ROW FORMAT DELIMITED " +
                "FIELDS TERMINATED BY '\t'" +
                "STORED AS TEXTFILE";
        preparedStatement=connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }
    @SneakyThrows
    public Boolean updateTable(String sql,String... p){
        getConnection();
        preparedStatement=connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        if(p!=null)
        for(int i=0;i<p.length;i++){
            preparedStatement.setString(i+1,p[i]);
        }
        boolean row=preparedStatement.execute();
        return row;
    }
    @SneakyThrows
    public ResultSet queryTable(String sql,String... p){
        getConnection();
        List<Letter> letterList=new ArrayList<>();
        preparedStatement=connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        if(p!=null)
            for(int i=0;i<p.length;i++){
                preparedStatement.setString(i+1,p[i]);
            }
        ResultSet resultSet=preparedStatement.executeQuery();
        return resultSet;
    }
    @SneakyThrows
    public void closeConnection(){
        if(preparedStatement!=null) preparedStatement.close();
        if(connection!=null) connection.close();
    }

}
