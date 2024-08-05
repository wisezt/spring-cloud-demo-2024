package com.ting.mobile.app.ws.ui.controller;

import com.ting.mobile.app.ws.ui.model.request.UserDetailsRequestModel;
import com.ting.mobile.app.ws.ui.model.response.UserRest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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


    @GetMapping(path = "/{userId}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {

        UserRest returnValue = new UserRest();
        returnValue.setEmail("test@test.com");
        returnValue.setFirstName("Ting");
        returnValue.setLastName("Zheng");
        return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);

//        return new ResponseEntity<UserRest>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<UserRest> createUser(
           @Valid @RequestBody UserDetailsRequestModel userDetails
    ) {
        UserRest returnValue = new UserRest();
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());
        return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
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
