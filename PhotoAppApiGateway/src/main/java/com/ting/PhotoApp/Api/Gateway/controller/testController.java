package com.ting.PhotoApp.Api.Gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/tests")
public class testController {

    @GetMapping
    public String getTests(){
        return "this is the test Controller!";
    }
}
