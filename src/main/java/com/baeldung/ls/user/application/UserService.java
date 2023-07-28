package com.baeldung.ls.user.application;

import com.baeldung.ls.user.application.command.CreateUserCommand;
import com.baeldung.ls.user.application.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse save(CreateUserCommand command);
    List<UserResponse> findAll();
}
