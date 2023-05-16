package com.example.userdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/web/html-controller")
public class GuestController {


    @GetMapping("/home")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/usersdemo")
    @ResponseBody
    public String usersdemo() {
        return "http://localhost:8443/userdemo/api/web/html-controller/users";
    }

    @GetMapping("/users")
    public String users() {

        return "users";
    }

}