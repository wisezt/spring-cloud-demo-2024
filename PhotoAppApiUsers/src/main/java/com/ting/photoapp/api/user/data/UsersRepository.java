package com.ting.photoapp.api.user.data;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);

}
