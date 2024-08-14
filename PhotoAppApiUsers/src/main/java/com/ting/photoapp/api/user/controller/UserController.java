package com.ting.photoapp.api.user.controller;

import com.ting.photoapp.api.user.ui.model.CreateUserRequestModel;
import com.ting.photoapp.api.user.ui.model.CreateUserResponseModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getUsers(){
        return "USRS";
    }


    @GetMapping("/status/check")
    public String getStatus(){
        return "Working...";
    }

    @PostMapping
    public CreateUserResponseModel createUser(@Valid @RequestBody CreateUserRequestModel requestModel){

        CreateUserResponseModel responseModel = new CreateUserResponseModel();
        responseModel.setUserId(UUID.randomUUID().toString());
        responseModel.setFirstName(requestModel.getFirstName());
        responseModel.setLastName(requestModel.getLastName());
        responseModel.setEmail(requestModel.getEmail());

        return responseModel;
    }


}
