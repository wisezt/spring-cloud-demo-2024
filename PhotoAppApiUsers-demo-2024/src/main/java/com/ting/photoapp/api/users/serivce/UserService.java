package com.ting.photoapp.api.users.serivce;

import com.ting.photoapp.api.users.shared.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDetails);
}

