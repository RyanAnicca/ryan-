package com.example.userdemo.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "userdetail")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dtid")
    private long dtid;
    // 不顯示(pk)
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
