package com.ting.photoapp.api.user.service;

import com.ting.photoapp.api.user.data.UserEntity;
import com.ting.photoapp.api.user.data.UsersRepository;
import com.ting.photoapp.api.user.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = usersRepository.findByEmail(username);

        if(userEntity == null){
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true,true,true, new ArrayList<>());
    }


    @Override
    public UserDTO getUserDetailsByEmail(String userName){
        UserEntity userEntity = usersRepository.findByEmail(userName);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);

        return userDTO;

    }
}



