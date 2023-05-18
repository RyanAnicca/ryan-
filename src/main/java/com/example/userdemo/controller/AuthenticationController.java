package com.example.userdemo.controller;


import com.example.userdemo.model.dto.AuthenticationRequest;
import com.example.userdemo.model.dto.AuthenticationResponse;
import com.example.userdemo.model.dto.RegisterRequest;
import com.example.userdemo.model.service.JwtService;
import com.example.userdemo.model.service.impl.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    private final AuthenticationService authenticationService;

    //註冊
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    //登入
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request, HttpServletResponse response, HttpSession session
    ) throws Exception {

        return ResponseEntity.ok(authenticationService.authenticate(request,response,session));
    }

    //登入狀態
    @GetMapping("/state")
    @ResponseBody
    public Boolean loginstate(
            HttpServletRequest request) {
        return authenticationService.loginstate(request);
    }

    //登出
    @PostMapping("/logoutusers")
    @ResponseBody
    public String logoutusers(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
        return "login";
    }

    @GetMapping("/getusersname")
    @ResponseBody
    public String getusersname() {
        return jwtService.getCurrentUsername();
    }

    @GetMapping("/getjwt")
    @ResponseBody
    public String getJwtFromSession(HttpSession session) {
        String jwt = (String) session.getAttribute("jwt");
        return jwt != null ? jwt : "";
    }
}
