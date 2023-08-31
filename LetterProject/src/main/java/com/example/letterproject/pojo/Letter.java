package com.example.letterproject.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Letter {
    private String originalId;
    private String letterType;
    private String letterTitle;
    private String letterRecipient;
    private String letterDate;
    private String letterContent;
    private String replyOrganization;
    private String replyDate;
    private String replyContent;

}
