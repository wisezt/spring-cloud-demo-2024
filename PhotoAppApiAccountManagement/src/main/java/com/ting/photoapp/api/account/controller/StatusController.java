package com.ting.photoapp.api.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class StatusController {

    @GetMapping
    public String getStatus(){
        return "This is /users in ACCOUNT-WS";
    }
}
