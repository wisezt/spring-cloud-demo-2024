package com.ting.photoapp.api.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {

    @GetMapping
    public String getTests(){
        return "This is /users from UERS-WS";
    }

    @GetMapping("/test01")
    public String getTes01(){
        return "tests01";
    }
}
