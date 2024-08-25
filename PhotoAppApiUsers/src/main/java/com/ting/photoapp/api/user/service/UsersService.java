package com.ting.photoapp.api.user.service;

import com.ting.photoapp.api.user.shared.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {

    public UserDTO createUserDTO(UserDTO userDTO);


    UserDTO getUserDetailsByEmail(String userName);
}
