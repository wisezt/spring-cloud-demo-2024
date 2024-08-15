package com.ting.photoapp.api.user.service;

import com.netflix.discovery.converters.Auto;
import com.ting.photoapp.api.user.data.UserEntity;
import com.ting.photoapp.api.user.data.UsersRepository;
import com.ting.photoapp.api.user.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImp implements UsersService{

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDTO createUserDTO(UserDTO userDTO) {

        userDTO.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setEncryptedPassword(encoder.encode(userDTO.getPassword()));

        usersRepository.save(userEntity);

        return userDTO;
    }
}



