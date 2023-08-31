package com.example.letterproject.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LetterEndNum {
    private int year;
    private int month;
    private long letter_num;
}
