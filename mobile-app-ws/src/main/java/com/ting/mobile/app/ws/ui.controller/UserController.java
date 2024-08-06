package com.ting.mobile.app.ws.ui.controller;

import com.ting.mobile.app.ws.ui.model.request.UserDetailsRequestModel;
import com.ting.mobile.app.ws.ui.model.response.UserRest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {
    Map<String, UserRest> users;


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

        if (users.containsKey(userId)) {
            return new ResponseEntity<UserRest>(users.get(userId), HttpStatus.OK);
        }

        return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
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
        String userId = UUID.randomUUID().toString();
        returnValue.setUserId(userId);
        if (users == null) {
            users = new HashMap<>();
        }
        users.put(userId, returnValue);


        return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
    }

    @PutMapping(
            path = "/{userId}",
            consumes = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<UserRest> updateUser(
            @PathVariable String userId,
            @RequestBody UserDetailsRequestModel userDetails
    ) {
        if (users != null & users.containsKey(userId)) {
            UserRest returnValue = new UserRest();
            returnValue.setEmail(userDetails.getEmail());
            returnValue.setFirstName(userDetails.getFirstName());
            returnValue.setLastName(userDetails.getLastName());
            returnValue.setUserId(userId);

            users.put(userId, returnValue);

            return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
        }

        return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(
            path = "/{userId}"
    )
    public ResponseEntity<UserRest> deteleUser(@PathVariable String userId) {

        if (users != null && users.containsKey(userId)){
            UserRest returnValue = users.get(userId);
            users.remove(userId);
            return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
        }

        return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
    }

}
