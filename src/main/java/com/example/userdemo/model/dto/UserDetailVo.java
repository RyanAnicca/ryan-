package com.example.userdemo.model.dto;

import java.util.Date;

public interface UserDetailVo {

    int getid();
    String getuseremail();

    String getusername();

    String getuserphone();

    Date getregisterdata();

    Date getupdatadata();

    Boolean getaccountlocked();

}
