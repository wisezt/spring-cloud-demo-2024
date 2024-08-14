package com.ting.photoapp.api.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getUsers(){
        return "USRS";
    }


    @GetMapping("/status/check")
    public String getStatus(){
        return "Working...";
    }

    @PostMapping
    public String createUser(){
        return "User Created";
    }


}
