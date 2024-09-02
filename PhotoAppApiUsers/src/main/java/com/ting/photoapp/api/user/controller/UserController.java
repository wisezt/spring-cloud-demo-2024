package com.ting.photoapp.api.user.controller;

import com.ting.photoapp.api.user.data.UserEntity;
import com.ting.photoapp.api.user.service.UsersService;
import com.ting.photoapp.api.user.shared.UserDTO;
import com.ting.photoapp.api.user.ui.model.CreateUserRequestModel;
import com.ting.photoapp.api.user.ui.model.CreateUserResponseModel;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UsersService usersService;

  @Autowired Environment env;

  @GetMapping
  public String getUsers() {

    System.out.println("test01:" + env.getProperty("test01"));

    System.out.println("test02:" + env.getProperty("test02"));


    return "USRS\t" + env.getProperty("token.secret");
  }

  @GetMapping("/status/check")
  public String getStatus() {
    return "Working...";
  }

  @PostMapping
  public ResponseEntity<CreateUserResponseModel> createUser(
      @Valid @RequestBody CreateUserRequestModel requestModel) {

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    UserDTO userDTO = modelMapper.map(requestModel, UserDTO.class);

    usersService.createUserDTO(userDTO);

    CreateUserResponseModel responseModel = modelMapper.map(userDTO, CreateUserResponseModel.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
  }
}
