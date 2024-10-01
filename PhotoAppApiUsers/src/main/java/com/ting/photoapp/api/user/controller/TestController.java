package com.ting.photoapp.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    Environment env;


    @GetMapping
    public String getTests(){

        String test01 = env.getProperty("test01");
        String test02 = env.getProperty("test02");
        String result = String.format(
                "This is /tests at USERS-WS\n\ttest01: %s\n\ttest02: %s", test01, test02
        );


        return result;
    }

    @GetMapping("/test01")
    public String getTes01(){
        return "tests01\t" + env.getProperty("token.secret");
    }
}
