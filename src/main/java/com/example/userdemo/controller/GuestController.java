package com.example.userdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/web/html-controller")
public class GuestController {

    @RequestMapping("/w")
    @ResponseBody
    public String wellcome() {
        return "wellcome";
    }

    @RequestMapping("/home")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}