package com.ting.photoapp.api.users.controller;

import com.ting.photoapp.api.users.model.CreateUserRequestModel;
import com.ting.photoapp.api.users.serivce.UserService;
import com.ting.photoapp.api.users.shared.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping
    public String getStatus(HttpServletRequest request){
        return "This is the /users in USERS-WS. PORT: " + request.getServerPort();
    }

    @PostMapping
    public String createUser(@Valid @RequestBody CreateUserRequestModel userDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        userService.createUser(userDto);

        return "new user is created";
    }




}
