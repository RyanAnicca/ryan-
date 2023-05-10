package com.example.userdemo.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailVoRequest {

    private String email;
    private String password;
    private String username;
    private String userphone;
    private Date registerdata;
    private Date updatadata;


}
