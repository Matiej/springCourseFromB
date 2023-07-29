package com.baeldung.ls.user.application;

import com.baeldung.ls.user.application.command.CreateUserCommand;
import com.baeldung.ls.user.domain.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity save(CreateUserCommand command);
    List<UserEntity> findAll();
    Optional<UserEntity> findUserById(Long id);
    void deleteUser(Long id);

}
