package com.example.letterproject.dao;

import com.example.letterproject.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LetterDataDao {
    int init();
    List<LetterStartNum> browseLetterStartNum();
    List<LetterEndNum> browseLetterEndNum();
    List<LetterType> browseLetterType();
    List<LetterToad> browseLetterToad();
    List<LetterOrganization> browseLetterOrganization();

}
