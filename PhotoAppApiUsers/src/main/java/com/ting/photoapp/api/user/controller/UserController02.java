package com.ting.photoapp.api.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("users")
@RestController
public class UserController02 {
    @GetMapping
    public String getUsers(){
        return "This is /users from UERS-WS";
    }

    @GetMapping("/user01")
    public String getUser01(){
        return "user01";
    }
}
