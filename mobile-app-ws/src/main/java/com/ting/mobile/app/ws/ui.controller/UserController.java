package com.ting.mobile.app.ws.ui.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {


//    @GetMapping
//    public String getUsers() {
//        return "get users was called";
//    }

    @GetMapping
    public String getUsers(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
            @RequestParam(value = "sort", defaultValue = "false", required = false) boolean sort
    ) {
        return "getUsers was called. page = " + page + "\tlimit = " + limit + "\tsort = " + sort;
    }


    @GetMapping(path = "/{userId}")
    public String getUser(@PathVariable String userId) {
        return "get user was called. userId = " + userId;
    }

    @PostMapping
    public String createUser() {
        return "create user was called";
    }

    @PutMapping
    public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping
    public String deteleUser() {
        return "delete user was called";
    }

}
