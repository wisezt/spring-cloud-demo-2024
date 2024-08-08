package com.ting.photoapp.api.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test02")
public class TestController {

    @GetMapping
    public String getTests(){
        return "tests02";
    }
}
