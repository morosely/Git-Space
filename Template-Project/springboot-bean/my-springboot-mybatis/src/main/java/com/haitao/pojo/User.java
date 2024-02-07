package com.haitao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long aid;
    private String userName;
    private String mobileNo;
    private String userCode;
}
