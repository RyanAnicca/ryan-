package com.example.userdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class GuestController {

    @GetMapping
    @ResponseBody
    public String wellcome() {
        return "wellcome";
    }

    @GetMapping("/home")
    public String index() {
        return "index";
    }
}