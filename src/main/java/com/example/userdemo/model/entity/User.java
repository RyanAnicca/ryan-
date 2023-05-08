package com.example.userdemo.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String email;
    // 會員帳號
    @Column
    private String password;
    // 會員密碼
    @Column(name = "username")
    private String username;
    // 會員姓名
    @Column(name = "userphone")
    private Integer userphone;
    // 電話
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "registerdata")
    private Date registerdata;
    // 會員建立日期

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "updatadata")
    private Date updatadata;
    // 會員修改日期

}
