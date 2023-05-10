package com.example.userdemo.controller;


import com.example.userdemo.model.dto.AuthenticationRequest;
import com.example.userdemo.model.dto.RegisterRequest;
import com.example.userdemo.model.service.impl.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request, HttpServletResponse response) throws IOException {

        if (authenticationService.register(request)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            // 回傳409表示衝突
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<Void> authenticate(
            @RequestBody AuthenticationRequest arequest, HttpServletResponse response, HttpServletRequest httpRequest) throws IOException {

        if (authenticationService.authenticate(arequest, response, httpRequest) == null) {
            // 被封鎖403
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (authenticationService.authenticate(arequest, response, httpRequest)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            // 帳密錯誤401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @GetMapping("/state")
    @ResponseBody
    public Boolean loginstate(
            HttpServletRequest request) {
        return authenticationService.loginstate(request);
    }

}
