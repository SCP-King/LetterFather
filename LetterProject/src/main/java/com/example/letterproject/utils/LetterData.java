package com.example.letterproject.utils;

import com.example.letterproject.service.LetterDateService;
import com.example.letterproject.service.LetterHiveService;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.io.InputStream;

@Component
public class LetterData implements CommandLineRunner {

    @Autowired
    private LetterDateService letterDateService;


    @SneakyThrows
    private  boolean sshHost(String commond){
        JSch jsch = new JSch();
        Session session = jsch.getSession("bill","billsaifu", 22);
        session.setPassword("732338737q");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setCommand(commond);

        InputStream in = channelExec.getInputStream();
        channelExec.connect();
        boolean flag=false;

        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                System.out.print(new String(tmp, 0, i));
            }
            if (channelExec.isClosed()) {
                if (in.available() > 0) continue;
                flag=channelExec.getExitStatus()==0;
                System.out.println("Exit status: " + channelExec.getExitStatus());
                break;
            }
        }
        channelExec.disconnect();
        session.disconnect();
        return flag;
    }
    @SneakyThrows
    public  void  initData() {
        boolean flag=false;
        System.out.println("==================开始爬取信件数据==================");
        LetterUtils letterUtils=new LetterUtils();
        letterUtils.loadLetters();
        System.out.println("==================爬取完成==================");
        System.out.println("本地上传数据到HDFS");
        LetterHDFS letterHDFS=new LetterHDFS();
        letterHDFS.init();
        letterHDFS.DelFile("/user/bill/letter_res");
        letterHDFS.CpFile("LetterProject/src/main/resources/static/data/letter_htmls","/user/bill/");
        System.out.println("==================网页数据上传完成==================");
        System.out.println("==================开始mapreduce清洗数据");
        flag=sshHost("cd test;hadoop jar LP.jar org.example.Main /user/bill/letter_htmls/www.beijing.gov.cn/  /user/bill/letter_res");
        if(flag) System.out.println("==================mapreduce成功==================");
        else {
            System.out.println("==================mapreduce失败==================");
            return;
        }
        LetterHiveService letterHiveService=new LetterHiveService();
        letterHiveService.createLetterTable();
        System.out.println("==================hive表格创建成功==================");

        if(letterHDFS.isFile("/user/bill/letter_res/part-r-00000"))
        letterHiveService.loadLetter("/user/bill/letter_res/part-r-00000");
        else {
            System.out.println("==================part-r-00000不存在==================");
            return;
        }
        letterHDFS.closeFS();
        letterHiveService.selectLetterByStartTime();
        letterHiveService.selectLetterByEndTime();
        letterHiveService.selectLetterByType();
        letterHiveService.selectLetterByToad();
        letterHiveService.selectLetterByOrganization();
        System.out.println("==================hive表格数据上传成功==================");
        letterDateService.init();
        flag=sshHost("sqoop export  " +
                "  --connect jdbc:mysql://billsaifu:3306/school?characterEncoding=utf8 " +
                "  --username root " +
                "  --password 123456 " +
                "  --table letter_start_num " +
                "  --export-dir /user/hive/warehouse/school.db/letter_start_num " +
                "  --input-fields-terminated-by \"\\t\" ");
        if (flag){
            System.out.println("==================letter_start_num导出成功==================");
        }
        else {
            System.out.println("==================letter_start_num导出失败==================");
            return;
        }
        flag=sshHost("sqoop export  " +
                "  --connect jdbc:mysql://billsaifu:3306/school?characterEncoding=utf8 " +
                "  --username root " +
                "  --password 123456 " +
                "  --table letter_end_num " +
                "  --export-dir /user/hive/warehouse/school.db/letter_end_num " +
                "  --input-fields-terminated-by \"\\t\" ");
        if (flag){
            System.out.println("==================letter_end_num导出成功==================");
        }
        else {
            System.out.println("==================letter_end_num导出失败==================");
            return;
        }
        flag=sshHost("sqoop export  " +
                "  --connect jdbc:mysql://billsaifu:3306/school?characterEncoding=utf8 " +
                "  --username root " +
                "  --password 123456 " +
                "  --table letter_type " +
                "  --export-dir /user/hive/warehouse/school.db/letter_type " +
                "  --input-fields-terminated-by \"\\t\" ");
        if (flag){
            System.out.println("==================letter_type导出成功==================");
        }
        else {
            System.out.println("==================letter_type导出失败==================");
            return;
        }

        flag=sshHost("sqoop export  " +
                "  --connect jdbc:mysql://billsaifu:3306/school?characterEncoding=utf8 " +
                "  --username root " +
                "  --password 123456 " +
                "  --table letter_toad " +
                "  --export-dir /user/hive/warehouse/school.db/letter_toad " +
                "  --input-fields-terminated-by \"\\t\" ");
        if (flag){
            System.out.println("==================letter_toad导出成功==================");
        }
        else {
            System.out.println("==================letter_toad导出失败==================");
            return;
        }

        flag=sshHost("sqoop export  " +
                "  --connect jdbc:mysql://billsaifu:3306/school?characterEncoding=utf8 " +
                "  --username root " +
                "  --password 123456 " +
                "  --table letter_organization " +
                "  --export-dir /user/hive/warehouse/school.db/letter_organization " +
                "  --input-fields-terminated-by \"\\t\" ");
        if (flag){
            System.out.println("==================letter_organization导出成功==================");
        }
        else {
            System.out.println("==================letter_organization导出失败==================");
            return;
        }
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==================初始化获取百姓信件数据==================");
        initData();
        System.out.println("==================信件数据初始化成功==================");
    }
}
