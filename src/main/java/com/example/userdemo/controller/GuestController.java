package com.example.userdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GuestController {

    @RequestMapping("/w")
    @ResponseBody
    public String wellcome() {
        return "wellcome";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}