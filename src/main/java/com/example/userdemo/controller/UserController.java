//package com.example.userdemo.controller;
//
//import com.example.userdemo.model.dto.AuthenticationResponse;
//import com.example.userdemo.model.dto.RegisterRequest;
//import com.example.userdemo.model.dto.UserDetailVo;
//import com.example.userdemo.model.dto.UserDetailVoRequest;
//import com.example.userdemo.model.entity.*;
//import com.example.userdemo.model.service.JwtService;
//import com.example.userdemo.model.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Objects;
//
//import static jdk.internal.logger.LoggerFinderLoader.service;
//
//@Controller
//@RequestMapping(value = "/user")
//public class UserController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping(value = "/register")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
//        return ResponseEntity.ok(service.register(request));
//    }
//
//    @PostMapping(value = "/authenticate")
//    @ResponseBody
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//
//        authenticate(authenticationRequest.getEmail(),
//                authenticationRequest.getPassword());
//
//        final UserDetails userDetails = userService
//                .loadUserByEmail(authenticationRequest.getEmail());
//
//        final String token = jwtService.generateToken(userDetails, remember);
//
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
////    @PostMapping(value = "/register")
////    @ResponseBody
////    public String saveUser(@RequestBody UserDetailVoRequest user) {
////        ResponseEntity.ok(userService.save(user));
////        return "註冊成功";
////    }
//
//
//
//
//    @PutMapping(value = "/updata")
//    @ResponseBody
//    public String updataUser(@RequestBody UserDetailVoRequest user) {
//        ResponseEntity.ok(userService.updateById(user));
//        return "更改成功";
//    }
//
//    // 依id來搜尋會員資料
//    @GetMapping("/selectbyid/{id}")
//    @ResponseBody
//    public UserDetailVo selectById(@PathVariable int id) {
//        return userService.selectById(id);
//    }
//
//    // 搜尋所有會員
//    @GetMapping("/selectalluser")
//    @ResponseBody
//    public List<User> selectAll() {
//        return userService.selectAll();
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        Objects.requireNonNull(username);
//        Objects.requireNonNull(password);
//
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
//}