package com.ting.photoapp.api.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/accounts")
public class AccoutController {

    @GetMapping
    public String getUsers(){
        return "ACCOUNTS";
    }

    @GetMapping("/account01")
    public String getStatus(){
        return "account01";
    }
}