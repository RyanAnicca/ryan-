package com.example.userdemo.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "user")
public class User implements UserDetails {
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

    // 帳號是否過期
    private Boolean accountnonexpired;
    // 帳號是否封鎖
    private Boolean accountnonlocked;
    // 憑證是否過期
    private Boolean iscredentialsnonexpired;
    // 帳號是否啟用
    private Boolean isenabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "updatadata")
    private Date updatadata;
    // 會員修改日期

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountnonexpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountnonlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return iscredentialsnonexpired;
    }

    @Override
    public boolean isEnabled() {
        return isenabled;
    }

}
