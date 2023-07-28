package com.baeldung.ls.user.application.impl;

import com.baeldung.ls.user.application.UserService;
import com.baeldung.ls.user.application.command.CreateUserCommand;
import com.baeldung.ls.user.application.response.UserResponse;
import com.baeldung.ls.user.database.UserRepository;
import com.baeldung.ls.user.domain.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponse save(CreateUserCommand command) {
        UserEntity userEntity = command.toUserEntity();
        UserEntity savedUser = repository.save(userEntity);
        return UserResponse.toCreateUserResponse(savedUser);
    }

    @Override
    public List<UserResponse> findAll() {
        return repository.findAll().stream().map(UserResponse::toCreateUserResponse).collect(Collectors.toList());
    }
}
