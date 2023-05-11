package com.example.userdemo.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private final AuthenticationProvider authenticationProvider;
//    @Autowired
//    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        try {
            http
                    // 關閉CSRF
                    .csrf().disable()
                    // Frame 同源設定
                    .headers(h -> h
                            .frameOptions().sameOrigin())
                    // 設定是否需要驗證的路徑(更改成使用註釋)
                    .authorizeHttpRequests(a -> a
                            .requestMatchers("/api/v1/auth/**","/home", "/test/**").permitAll()
                    )
                    //無權限 跳轉
//                    .exceptionHandling()
//                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    // 啟用jwt監聽
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            // 登入頁面
//                    .formLogin(formLogin -> formLogin
//                            .loginPage("/login")
//                            .permitAll())

            // 登出頁面
//                    .logout(logout -> logout
//                            .logoutUrl("/logout")
//                            .logoutSuccessHandler(logoutSuccessHandler)
//                            .logoutSuccessUrl("/")
//                            .permitAll())
            // 若無權限指定路徑
//                    .exceptionHandling()
//                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            return http.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}