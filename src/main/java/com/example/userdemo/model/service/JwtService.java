package com.example.userdemo.model.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class JwtService {


    // 清空雙JWT
    public void removeToken(HttpServletResponse response) {
        Cookie accessTokenCookie = setCookie(MyConstants.JWT_COOKIE_NAME,
                null);
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = setCookie(MyConstants.JWT_REFRESH_COOKIE_NAME,
                null);
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);

        Cookie googleCookie = setCookie(MyConstants.GOOGLE_COOKIE_NAME,
                null);
        googleCookie.setMaxAge(0);
        googleCookie.setHttpOnly(true);
        googleCookie.setSecure(true);
        googleCookie.setPath("/morari");;
        response.addCookie(googleCookie);
    }
}
