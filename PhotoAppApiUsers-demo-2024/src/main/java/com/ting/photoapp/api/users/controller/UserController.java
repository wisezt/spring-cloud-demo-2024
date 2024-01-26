package com.ting.photoapp.api.users.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getStatus(HttpServletRequest request){
        return "This is the /users in USERS-WS. PORT: " + request.getServerPort();
    }

    @GetMapping("/create")
    public String createUser(){
        return "this is the userinusers.";
    }




}
