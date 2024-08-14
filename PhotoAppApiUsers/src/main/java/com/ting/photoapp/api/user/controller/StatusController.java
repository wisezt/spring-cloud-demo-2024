package com.ting.photoapp.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class StatusController {


    @Autowired
    private Environment env;


    @GetMapping
    public String getPort(){
        return "USERS-WS Port: " + env.getProperty("local.server.port");
    }


}
