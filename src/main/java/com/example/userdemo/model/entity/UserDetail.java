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
    @Column(name = "id")
    private Integer id;
    // 不顯示(pk)
    @Column(name = "userid")
    private String userid;
    // 會員 ID
    @Column(name = "username")
    private Integer username;
    // 會員姓名
    @Column(name = "userphone")
    private Integer userphone;
    // 電話
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "registerdata")
    private Date registerdata;
    // 會員建立日期

}
