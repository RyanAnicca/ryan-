package com.example.userdemo.model.service;

import com.example.userdemo.config.MyConstants;
import com.example.userdemo.model.dto.AuthenticationResponse;
import com.example.userdemo.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    private static final String SECRET_KEY = "78214125442A462D4A614E645267556B58703273357638792F423F4528482B4B6250655368566D597133743677397A24432646294A404E635166546A576E5A7234753778214125442A472D4B6150645367556B58703273357638792F423F4528482B4D6251655468576D597133743677397A24432646294A404E635266556A586E327234753778214125442A472D4B6150645367566B59703373367638792F423F4528482B4D6251655468576D5A7134743777217A24432646294A404E635266556A586E3272357538782F413F442A472D4B6150645367566B59703373367639792442264529482B4D6251655468576D5A7134743777217A25432A462D4A614E635266556A586E3272357538782F413F4428472B4B6250655367566B5970337336763979244226452948404D635166546A576D5A7134743777217A25432A462D4A614E645267556B58703272357538782F413F4428472B4B6250655368566D5971337436763979244226452948404D635166546A576E5A7234753778217A25432A462D4A614E645267556B58703273357638792F423F4528472B4B6250655368566D597133743677397A24432646294A404D635166546A576E5A7234753778214125442A472D4B6150645267556B58703273357638792F423F4528482B4D6251655468566D597133743677397A24432646294A404E635266556A586E5A723475";

    // 拿取使用者帳號
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 拿取指定內容
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public AuthenticationResponse generateToken(UserDetails userDetails, Boolean rememberMe) {
        return generateToken(new HashMap<>(), userDetails, rememberMe);
    }

//    public String generateToken(
//            Map<String, Object> extraClaims,
//            UserDetails userDetails
//    ) {
//        return Jwts
//                .builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

    // 驗證令牌是否有效
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 拿取過期時間
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 製作JWT
    public AuthenticationResponse generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            Boolean rememberMe) {
        // 放自定義物件
        User user = (User) userDetails;
        extraClaims.put("id", user.getId());
        extraClaims.put("remember", rememberMe);
        extraClaims.put("lastLoginTime", String.valueOf(System.currentTimeMillis()));
        // 製作refreshToken
        String refreshToken = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + (rememberMe ? MyConstants.REMEMBER_REFRESH_TOKEN_VALIDATION_SECOND
                                : MyConstants.REFRESH_TOKEN_VALIDATION_SECOND)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
        // 製作accessToken
        String accessToken = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (MyConstants.ACCESS_TOKEN_VALIDATION_SECOND)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
        return new AuthenticationResponse(accessToken, refreshToken, "bearer");
    }

    // 設置HttpOnly&Https的Cookie
    public Cookie setCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    // Cookie拿取JWT
    public String getToken(Cookie[] cookies, String jwtName) {
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(jwtName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
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
        googleCookie.setPath("/morari");
        ;
        response.addCookie(googleCookie);
    }
}
