package com.example.userdemo.model.service.impl;

import com.example.userdemo.config.MyConstants;
import com.example.userdemo.model.dto.AuthenticationRequest;
import com.example.userdemo.model.dto.AuthenticationResponse;
import com.example.userdemo.model.dto.RegisterRequest;
import com.example.userdemo.model.entity.Users;
import com.example.userdemo.model.repository.UserDao;
import com.example.userdemo.model.service.JwtService;
import com.example.userdemo.token.Tokens;
import com.example.userdemo.model.repository.TokenRepository;
import com.example.userdemo.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserDao userDao;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    // 註冊
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        Date now = new Date();
        // Name
        Users users = Users.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .userphone(request.getUserphone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .registerdata(now)
                .updatadata(null)
                .accountnonexpired(true)
                .iscredentialsnonexpired(true)
                .isenabled(true)
                .accountnonlocked(true)
                .build();
        userDao.save(users);
        Users newuser;
        newuser = userDao.findByEmail(users.getEmail());
        users.setId(newuser.getId());
        var jwtToken = jwtService.generateToken(users);
        var refreshToken = jwtService.generateRefreshToken(users);
        saveUserToken(users, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 登入
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response, HttpSession session) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            Users users = userDao.findByEmail(request.getEmail());
            if (!(users.getAccountnonexpired() && users.getAccountnonlocked() && users.isEnabled()
                    && users.isCredentialsNonExpired())) {
                jwtService.removeToken(session);
                response.sendRedirect("/morari/login?error=user_not_authorized");
            }

            var jwtToken = jwtService.generateToken(users);
            var refreshToken = jwtService.generateRefreshToken(users);
            revokeAllUserTokens(users);
            saveUserToken(users, jwtToken);
            session.setAttribute("jwt", jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    // 登入狀態
    public Boolean loginstate(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }
        String sessionjwt = authorizationHeader.replace("Bearer ", "");
        Boolean islogin = false;
        try {
            String userEmail = jwtService.extractUsername(sessionjwt);
            if (userEmail != null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                islogin = jwtService.isTokenValid(sessionjwt, userDetails);
            }
        } catch (Exception e) {
            islogin = false;
        }
        return islogin;
    }

    private void saveUserToken(Users users, String jwtToken) {
        var tokens = Tokens.builder()
                .users(users)
                .tokens(jwtToken)
                .tokenType("BEARER")
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(tokens);
    }

    private void revokeAllUserTokens(Users users) {
        List<Tokens> validUserTokens = tokenRepository.findAllValidTokenByUser(users.getId());
        if (validUserTokens.isEmpty())
            return;
        for (Tokens tokens : validUserTokens) {
            tokens.setExpired(true);
            tokens.setRevoked(true);
            tokenRepository.updateToken(tokens);
        }
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userDao.findByEmail(userEmail);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
