package com.example.letterproject.utils;
import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.net.URI;

public class LetterHDFS {
    private FileSystem fs = null;

    @SneakyThrows
    public void init(){
        Configuration conf = new Configuration();
        conf.set("dfs.client.use.datanode.hostname", "true");
        conf.set("fs.defaultFS", "hdfs://billsaifu:9000");
        URI uri=new URI("hdfs://billsaifu:9000");
        String username="bill";
        fs=FileSystem.get(uri,conf,username);
    }
    @SneakyThrows
    public void Mkdir(String filepath){
        fs.mkdirs(new Path(filepath));
    }
    @SneakyThrows
    public void CpFile(String localPath,String filePath){
        fs.copyFromLocalFile(false,true,new Path(localPath),new Path(filePath));
    }
    @SneakyThrows
    public void GetFile(String filePath,String localPath){
        fs.copyToLocalFile(false,new Path(filePath),new Path(localPath),true);
    }
    @SneakyThrows
    public void DelFile(String filePath){
        fs.delete(new Path(filePath));
    }
    @SneakyThrows
    public void MvFile(String OldFilePath,String NewFilePath){
        fs.rename(new Path(OldFilePath),new Path(NewFilePath));
    }
    @SneakyThrows
    public void FileDetail(String filePath){
        RemoteIterator<LocatedFileStatus> listFiles=fs.listFiles(new Path(filePath),true);
        while (listFiles.hasNext()){
            LocatedFileStatus one=listFiles.next();
            System.out.println("文件路径: "+one.getPath());
            System.out.println("文件名: "+one.getPath().getName());
            System.out.println("文件所有者: "+one.getOwner());
            System.out.println("文件所有组: "+one.getGroup());
            System.out.println("文件大小: "+one.getLen());
        }
    }
    @SneakyThrows
    public boolean isFile(String filePath){
        return fs.exists(new Path(filePath));
    }
    @SneakyThrows
    public void closeFS(){
        if(fs!=null) fs.close();
    }
}
