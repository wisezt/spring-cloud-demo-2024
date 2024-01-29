package com.ting.photoapp.api.users.controller;

import com.ting.photoapp.api.users.model.CreateUserRequestModel;
import com.ting.photoapp.api.users.model.CreateUserResponseModel;
import com.ting.photoapp.api.users.serivce.UserService;
import com.ting.photoapp.api.users.shared.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping
    public String getStatus(HttpServletRequest request) {

        return "This is the /users in USERS-WS.IP: " + request.getRemoteAddr() + " PORT: " + request.getServerPort();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails, HttpServletRequest request) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        // Save user into H2 DB
        userService.createUser(userDto);

        CreateUserResponseModel createUserResponseModel = modelMapper.map(userDto, CreateUserResponseModel.class);

        System.out.println("request IP: " + request.getRemoteAddr() + " " + request.getServerPort());

        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);
    }


}
