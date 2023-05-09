package com.example.userdemo.model.service.impl;

import com.example.userdemo.config.MyConstants;
import com.example.userdemo.model.dto.AuthenticationRequest;
import com.example.userdemo.model.dto.AuthenticationResponse;
import com.example.userdemo.model.dto.RegisterRequest;
import com.example.userdemo.model.entity.Role;
import com.example.userdemo.model.entity.User;
import com.example.userdemo.model.repository.RoleRepository;
import com.example.userdemo.model.repository.UserDao;
import com.example.userdemo.model.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDao userDao;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    // 註冊
    @Transactional
    public Boolean register(RegisterRequest request) {
        try {
            Date now = new Date();
            // Name
            User user = User.builder()
                    .username(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .registerdata(now)
                    .updatadata(null)
                    .build();
            userDao.insert(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 登入
    @Transactional
    public Boolean authenticate(AuthenticationRequest request, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            User userProfiles = userDao.findByEmail(request.getEmail());
            // 權限是否正常
            if (!(userProfiles.getAccountnonexpired() && userProfiles.getAccountnonlocked() && userProfiles.isEnabled()
                    && userProfiles.isCredentialsNonExpired())) {
                jwtService.removeToken(response);
                response.sendRedirect("/morari/login?error=user_not_authorized");
            }
            AuthenticationResponse authenticationResponse = jwtService.generateToken(userProfiles,
                    request.getRememberMe());
            jwtService.removeToken(response, authenticationResponse);
            return true;
        }
        catch (LockedException e) {
            // e.printStackTrace();
            return null;
        }
        catch (AccountExpiredException e) {
            // e.printStackTrace();
            return null;
        }
        catch (DisabledException e) {
            // e.printStackTrace();
            return null;
        }
        catch (CredentialsExpiredException e) {
            // e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    // 登入狀態
    public Boolean loginstate(HttpServletRequest request) {
        HttpSession Session = request.getSession();
        String sessionjwt = null;
        Boolean islogin = false;
        if (Session != null) {
            sessionjwt = jwtService.getToken(Session, MyConstants.JWT_COOKIE_NAME);
        }
        if (sessionjwt == null || sessionjwt.isEmpty()) {
            return false;
        }
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


}
