package com.ting.photoapp.api.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getStatus(){
        return "This is the /users in USERS-WS.";
    }

    @GetMapping("/userinusers")
    public String getUserinUsers(){
        return "this is the userinusers.";
    }




}
